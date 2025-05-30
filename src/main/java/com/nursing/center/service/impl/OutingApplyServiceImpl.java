package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:59
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.enums.ApplyStatus;
import com.nursing.center.common.enums.BedStatus;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.common.utils.SecurityUtils;
import com.nursing.center.dto.OutingApplyDTO;
import com.nursing.center.dto.OutingApplyQueryDTO;
import com.nursing.center.dto.OutingApproveDTO;
import com.nursing.center.dto.OutingReturnDTO;
import com.nursing.center.entity.Bed;
import com.nursing.center.entity.Customer;
import com.nursing.center.entity.OutingApply;
import com.nursing.center.mapper.BedMapper;
import com.nursing.center.mapper.CustomerMapper;
import com.nursing.center.mapper.OutingApplyMapper;
import com.nursing.center.service.OutingApplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutingApplyServiceImpl implements OutingApplyService {

    private final OutingApplyMapper outingApplyMapper;
    private final CustomerMapper customerMapper;
    private final BedMapper bedMapper;

    @Override
    public IPage<OutingApplyDTO> getOutingApplyPage(OutingApplyQueryDTO query) {
        Page<OutingApplyDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return outingApplyMapper.selectOutingApplyPage(page, query);
    }

    @Override
    public OutingApplyDTO getOutingApplyById(Long id) {
        return outingApplyMapper.selectOutingApplyById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitOutingApply(OutingApplyDTO outingApplyDTO) {
        // 验证客户是否存在且在住
        Customer customer = customerMapper.selectById(outingApplyDTO.getCustomerId());
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }
        if (customer.getStatus() != 1) {
            throw new BusinessException("客户不在住状态，无法申请外出");
        }

        // 验证时间逻辑
        if (outingApplyDTO.getExpectedReturnDate().isBefore(outingApplyDTO.getOutingDate())) {
            throw new BusinessException("预计回院时间不能早于外出时间");
        }

        // 检查是否已有未处理的外出申请
        checkExistingOutingApply(outingApplyDTO.getCustomerId());

        // 创建外出申请
        OutingApply outingApply = new OutingApply();
        BeanUtils.copyProperties(outingApplyDTO, outingApply);

        // 设置申请人（如果没有指定，使用当前用户）
        if (outingApply.getApplicantId() == null) {
            outingApply.setApplicantId(SecurityUtils.getCurrentUserId());
        }

        // 设置申请状态为已提交
        outingApply.setApplyStatus(ApplyStatus.SUBMITTED);

        outingApplyMapper.insert(outingApply);

        log.info("提交外出申请成功，申请ID: {}, 客户ID: {}, 申请人ID: {}",
                outingApply.getId(), outingApply.getCustomerId(), outingApply.getApplicantId());

        return outingApply.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveOutingApply(OutingApproveDTO approveDTO) {
        // 查询申请信息
        OutingApply outingApply = outingApplyMapper.selectById(approveDTO.getApplyId());
        if (outingApply == null) {
            throw new BusinessException("外出申请不存在");
        }

        if (!ApplyStatus.SUBMITTED.equals(outingApply.getApplyStatus())) {
            throw new BusinessException("申请已处理，无法重复审批");
        }

        // 更新审批信息
        OutingApply updateApply = new OutingApply();
        updateApply.setId(approveDTO.getApplyId());
        updateApply.setApplyStatus(approveDTO.getApplyStatus());
        updateApply.setApproverId(SecurityUtils.getCurrentUserId());
        updateApply.setApproveTime(LocalDateTime.now());
        updateApply.setApproveRemark(approveDTO.getApproveRemark());

        outingApplyMapper.updateById(updateApply);

        // 如果审批通过，更新床位状态为外出
        if (ApplyStatus.APPROVED.equals(approveDTO.getApplyStatus())) {
            handleApprovedOuting(outingApply);
        }

        log.info("外出申请审批完成，申请ID: {}, 审批结果: {}, 审批人: {}",
                approveDTO.getApplyId(), approveDTO.getApplyStatus(), SecurityUtils.getCurrentUserId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerReturn(OutingReturnDTO returnDTO) {
        // 查询申请信息
        OutingApply outingApply = outingApplyMapper.selectById(returnDTO.getApplyId());
        if (outingApply == null) {
            throw new BusinessException("外出申请不存在");
        }

        if (!ApplyStatus.APPROVED.equals(outingApply.getApplyStatus())) {
            throw new BusinessException("只有审批通过的申请才能登记回院");
        }

        if (outingApply.getActualReturnDate() != null) {
            throw new BusinessException("该申请已登记回院");
        }

        // 验证回院时间不能早于外出时间
        if (returnDTO.getActualReturnDate().isBefore(outingApply.getOutingDate())) {
            throw new BusinessException("回院时间不能早于外出时间");
        }

        // 更新实际回院时间
        OutingApply updateApply = new OutingApply();
        updateApply.setId(returnDTO.getApplyId());
        updateApply.setActualReturnDate(returnDTO.getActualReturnDate());

        outingApplyMapper.updateById(updateApply);

        // 恢复床位状态
        handleCustomerReturn(outingApply.getCustomerId());

        log.info("登记回院成功，申请ID: {}, 客户ID: {}, 回院时间: {}",
                returnDTO.getApplyId(), outingApply.getCustomerId(), returnDTO.getActualReturnDate());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOutingApply(Long id) {
        OutingApply outingApply = outingApplyMapper.selectById(id);
        if (outingApply == null) {
            throw new BusinessException("外出申请不存在");
        }

        // 只能删除已提交状态的申请
        if (!ApplyStatus.SUBMITTED.equals(outingApply.getApplyStatus())) {
            throw new BusinessException("只能删除未审批的申请");
        }

        outingApplyMapper.deleteById(id);

        log.info("删除外出申请成功，申请ID: {}", id);
    }

    /**
     * 检查是否已有未处理的外出申请
     */
    private void checkExistingOutingApply(Long customerId) {
        LambdaQueryWrapper<OutingApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutingApply::getCustomerId, customerId)
                .eq(OutingApply::getApplyStatus, ApplyStatus.SUBMITTED);

        OutingApply existApply = outingApplyMapper.selectOne(wrapper);
        if (existApply != null) {
            throw new BusinessException("该客户已有未处理的外出申请");
        }

        // 检查是否有已审批通过但未回院的申请
        LambdaQueryWrapper<OutingApply> approvedWrapper = new LambdaQueryWrapper<>();
        approvedWrapper.eq(OutingApply::getCustomerId, customerId)
                .eq(OutingApply::getApplyStatus, ApplyStatus.APPROVED)
                .isNull(OutingApply::getActualReturnDate);

        OutingApply approvedApply = outingApplyMapper.selectOne(approvedWrapper);
        if (approvedApply != null) {
            throw new BusinessException("该客户已有审批通过未回院的外出申请");
        }
    }

    /**
     * 处理审批通过的外出申请
     */
    private void handleApprovedOuting(OutingApply outingApply) {
        // 查询客户信息
        Customer customer = customerMapper.selectById(outingApply.getCustomerId());
        if (customer != null && customer.getBedId() != null) {
            // 更新床位状态为外出
            Bed updateBed = new Bed();
            updateBed.setId(customer.getBedId());
            updateBed.setBedStatus(BedStatus.OUT);
            bedMapper.updateById(updateBed);

            log.info("客户外出处理完成，客户ID: {}, 床位ID: {} 状态更新为外出",
                    customer.getId(), customer.getBedId());
        }
    }

    /**
     * 处理客户回院
     */
    private void handleCustomerReturn(Long customerId) {
        // 查询客户信息
        Customer customer = customerMapper.selectById(customerId);
        if (customer != null && customer.getBedId() != null) {
            // 恢复床位状态为有人
            Bed updateBed = new Bed();
            updateBed.setId(customer.getBedId());
            updateBed.setBedStatus(BedStatus.OCCUPIED);
            bedMapper.updateById(updateBed);

            log.info("客户回院处理完成，客户ID: {}, 床位ID: {} 状态恢复为有人",
                    customer.getId(), customer.getBedId());
        }
    }
}
