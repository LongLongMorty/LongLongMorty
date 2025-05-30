package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:58
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.enums.ApplyStatus;
import com.nursing.center.common.enums.BedStatus;
import com.nursing.center.common.enums.CheckoutType;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.common.utils.SecurityUtils;
import com.nursing.center.dto.CheckoutApplyDTO;
import com.nursing.center.dto.CheckoutApplyQueryDTO;
import com.nursing.center.dto.CheckoutApproveDTO;
import com.nursing.center.entity.Bed;
import com.nursing.center.entity.CheckoutApply;
import com.nursing.center.entity.Customer;
import com.nursing.center.mapper.BedMapper;
import com.nursing.center.mapper.CheckoutApplyMapper;
import com.nursing.center.mapper.CustomerMapper;
import com.nursing.center.service.CheckoutApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutApplyServiceImpl implements CheckoutApplyService {

    private final CheckoutApplyMapper checkoutApplyMapper;
    private final CustomerMapper customerMapper;
    private final BedMapper bedMapper;

    @Override
    public IPage<CheckoutApplyDTO> getCheckoutApplyPage(CheckoutApplyQueryDTO query) {
        Page<CheckoutApplyDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return checkoutApplyMapper.selectCheckoutApplyPage(page, query);
    }

    @Override
    public CheckoutApplyDTO getCheckoutApplyById(Long id) {
        return checkoutApplyMapper.selectCheckoutApplyById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitCheckoutApply(CheckoutApplyDTO checkoutApplyDTO) {
        // 验证客户是否存在且在住
        Customer customer = customerMapper.selectById(checkoutApplyDTO.getCustomerId());
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }
        if (customer.getStatus() != 1) {
            throw new BusinessException("客户不在住状态，无法申请退住");
        }

        // 检查是否已有未处理的退住申请
        checkExistingCheckoutApply(checkoutApplyDTO.getCustomerId());

        // 创建退住申请
        CheckoutApply checkoutApply = new CheckoutApply();
        BeanUtils.copyProperties(checkoutApplyDTO, checkoutApply);

        // 设置申请人（如果没有指定，使用当前用户）
        if (checkoutApply.getApplicantId() == null) {
            checkoutApply.setApplicantId(SecurityUtils.getCurrentUserId());
        }

        // 设置申请状态为已提交
        checkoutApply.setApplyStatus(ApplyStatus.SUBMITTED);

        checkoutApplyMapper.insert(checkoutApply);

        log.info("提交退住申请成功，申请ID: {}, 客户ID: {}, 申请人ID: {}",
                checkoutApply.getId(), checkoutApply.getCustomerId(), checkoutApply.getApplicantId());

        return checkoutApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveCheckoutApply(CheckoutApproveDTO approveDTO) {
        // 查询申请信息
        CheckoutApply checkoutApply = checkoutApplyMapper.selectById(approveDTO.getApplyId());
        if (checkoutApply == null) {
            throw new BusinessException("退住申请不存在");
        }

        if (!ApplyStatus.SUBMITTED.equals(checkoutApply.getApplyStatus())) {
            throw new BusinessException("申请已处理，无法重复审批");
        }

        // 更新审批信息
        CheckoutApply updateApply = new CheckoutApply();
        updateApply.setId(approveDTO.getApplyId());
        updateApply.setApplyStatus(approveDTO.getApplyStatus());
        updateApply.setApproverId(SecurityUtils.getCurrentUserId());
        updateApply.setApproveTime(LocalDateTime.now());
        updateApply.setApproveRemark(approveDTO.getApproveRemark());

        checkoutApplyMapper.updateById(updateApply);

        // 如果审批通过且是正常退住或死亡退住，需要处理客户状态和床位状态
        if (ApplyStatus.APPROVED.equals(approveDTO.getApplyStatus())) {
            handleApprovedCheckout(checkoutApply);
        }

        log.info("退住申请审批完成，申请ID: {}, 审批结果: {}, 审批人: {}",
                approveDTO.getApplyId(), approveDTO.getApplyStatus(), SecurityUtils.getCurrentUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCheckoutApply(Long id) {
        CheckoutApply checkoutApply = checkoutApplyMapper.selectById(id);
        if (checkoutApply == null) {
            throw new BusinessException("退住申请不存在");
        }

        // 只能删除已提交状态的申请
        if (!ApplyStatus.SUBMITTED.equals(checkoutApply.getApplyStatus())) {
            throw new BusinessException("只能删除未审批的申请");
        }

        checkoutApplyMapper.deleteById(id);

        log.info("删除退住申请成功，申请ID: {}", id);
    }

    /**
     * 检查是否已有未处理的退住申请
     */
    private void checkExistingCheckoutApply(Long customerId) {
        LambdaQueryWrapper<CheckoutApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckoutApply::getCustomerId, customerId)
                .eq(CheckoutApply::getApplyStatus, ApplyStatus.SUBMITTED);

        CheckoutApply existApply = checkoutApplyMapper.selectOne(wrapper);
        if (existApply != null) {
            throw new BusinessException("该客户已有未处理的退住申请");
        }
    }

    /**
     * 处理审批通过的退住申请
     */
    private void handleApprovedCheckout(CheckoutApply checkoutApply) {
        // 只有正常退住和死亡退住需要释放床位
        if (CheckoutType.NORMAL.equals(checkoutApply.getCheckoutType()) ||
                CheckoutType.DEATH.equals(checkoutApply.getCheckoutType())) {

            // 查询客户信息
            Customer customer = customerMapper.selectById(checkoutApply.getCustomerId());
            if (customer != null && customer.getBedId() != null) {
                // 更新客户状态为已退住
                Customer updateCustomer = new Customer();
                updateCustomer.setId(checkoutApply.getCustomerId());
                updateCustomer.setStatus(0); // 已退住
                customerMapper.updateById(updateCustomer);

                // 释放床位
                Bed updateBed = new Bed();
                updateBed.setId(customer.getBedId());
                updateBed.setBedStatus(BedStatus.AVAILABLE);
                bedMapper.updateById(updateBed);

                log.info("客户退住处理完成，客户ID: {}, 床位ID: {} 已释放",
                        customer.getId(), customer.getBedId());
            }
        }
        // 保留床位类型不释放床位
    }
}
