package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:41
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.CareRecordDTO;
import com.nursing.center.dto.CareRecordQueryDTO;
import com.nursing.center.entity.CareRecord;
import com.nursing.center.entity.CustomerCare;
import com.nursing.center.mapper.CareRecordMapper;
import com.nursing.center.mapper.CustomerCareMapper;
import com.nursing.center.service.CareRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareRecordServiceImpl implements CareRecordService {

    private final CareRecordMapper careRecordMapper;
    private final CustomerCareMapper customerCareMapper;

    @Override
    public IPage<CareRecordDTO> getCareRecordPage(CareRecordQueryDTO query) {
        Page<CareRecordDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return careRecordMapper.selectCareRecordPage(page, query);
    }

    @Override
    public IPage<CareRecordDTO> getCustomerCareRecords(Long customerId, Integer pageNum, Integer pageSize) {
        Page<CareRecordDTO> page = new Page<>(pageNum, pageSize);
        return careRecordMapper.selectByCustomerId(page, customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCareRecord(CareRecordDTO careRecordDTO) {
        // 验证客户护理服务是否存在且有效
        CustomerCare customerCare = validateCustomerCareService(
                careRecordDTO.getCustomerId(),
                careRecordDTO.getCareItemId()
        );

        // 检查护理数量是否超出剩余数量
        if (careRecordDTO.getCareQuantity() > customerCare.getRemainingQuantity()) {
            throw new BusinessException("护理数量超出剩余可用数量");
        }

        // 创建护理记录
        CareRecord careRecord = new CareRecord();
        BeanUtils.copyProperties(careRecordDTO, careRecord);

        if (careRecord.getCareTime() == null) {
            careRecord.setCareTime(LocalDateTime.now());
        }

        careRecordMapper.insert(careRecord);

        // 更新客户护理服务的使用数量
        int newUsedQuantity = customerCare.getUsedQuantity() + careRecordDTO.getCareQuantity();
        int newRemainingQuantity = customerCare.getRemainingQuantity() - careRecordDTO.getCareQuantity();

        customerCareMapper.updateUsedQuantity(customerCare.getId(), newUsedQuantity, newRemainingQuantity);

        log.info("新增护理记录成功，记录ID: {}, 客户ID: {}, 护理项目ID: {}",
                careRecord.getId(), careRecord.getCustomerId(), careRecord.getCareItemId());

        return careRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCareRecord(Long id) {
        CareRecord careRecord = careRecordMapper.selectById(id);
        if (careRecord == null) {
            throw new BusinessException("护理记录不存在");
        }

        // 恢复客户护理服务的使用数量
        CustomerCare customerCare = validateCustomerCareService(
                careRecord.getCustomerId(),
                careRecord.getCareItemId()
        );

        int newUsedQuantity = customerCare.getUsedQuantity() - careRecord.getCareQuantity();
        int newRemainingQuantity = customerCare.getRemainingQuantity() + careRecord.getCareQuantity();

        // 确保数量不为负数
        newUsedQuantity = Math.max(0, newUsedQuantity);
        newRemainingQuantity = Math.min(customerCare.getPurchaseQuantity(), newRemainingQuantity);

        customerCareMapper.updateUsedQuantity(customerCare.getId(), newUsedQuantity, newRemainingQuantity);

        // 删除护理记录
        careRecordMapper.deleteById(id);

        log.info("删除护理记录成功，记录ID: {}", id);
    }

    @Override
    public CareRecordDTO getCareRecordById(Long id) {
        // 这里需要关联查询，暂时简单实现
        CareRecord careRecord = careRecordMapper.selectById(id);
        if (careRecord == null) {
            return null;
        }

        CareRecordDTO dto = new CareRecordDTO();
        BeanUtils.copyProperties(careRecord, dto);
        return dto;
    }

    /**
     * 验证客户护理服务是否存在且有效
     */
    private CustomerCare validateCustomerCareService(Long customerId, Long careItemId) {
        // 查询客户护理服务
        com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<CustomerCare> wrapper =
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<>();
        wrapper.eq(CustomerCare::getCustomerId, customerId)
                .eq(CustomerCare::getCareItemId, careItemId);

        CustomerCare customerCare = customerCareMapper.selectOne(wrapper);
        if (customerCare == null) {
            throw new BusinessException("客户未购买该护理项目");
        }

        // 检查服务是否到期
        if (customerCare.getExpireDate().isBefore(java.time.LocalDate.now())) {
            throw new BusinessException("护理服务已到期");
        }

        return customerCare;
    }
}
