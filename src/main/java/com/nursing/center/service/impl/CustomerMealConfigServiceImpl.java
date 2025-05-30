package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:31
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.*;
import com.nursing.center.entity.Customer;
import com.nursing.center.entity.CustomerMealConfig;
import com.nursing.center.mapper.CustomerMapper;
import com.nursing.center.mapper.CustomerMealConfigMapper;
import com.nursing.center.service.CustomerMealConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerMealConfigServiceImpl implements CustomerMealConfigService {

    private final CustomerMealConfigMapper customerMealConfigMapper;
    private final CustomerMapper customerMapper;

    @Override
    public IPage<CustomerMealConfigDTO> getCustomerMealConfigPage(CustomerMealConfigQueryDTO query) {
        Page<CustomerMealConfigDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return customerMealConfigMapper.selectCustomerMealConfigPage(page, query);
    }

    @Override
    public List<CustomerMealConfigDTO> getCustomerMealConfigs(Long customerId) {
        return customerMealConfigMapper.selectByCustomerId(customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCustomerMealConfig(CustomerMealConfigDTO configDTO) {
        // 验证客户是否存在
        validateCustomer(configDTO.getCustomerId());

        // 检查是否已存在相同餐类型的配置
        checkMealConfigConflict(configDTO.getCustomerId(), configDTO.getMealType().name(), null);

        CustomerMealConfig config = new CustomerMealConfig();
        BeanUtils.copyProperties(configDTO, config);
        config.setStatus(1); // 默认启用

        customerMealConfigMapper.insert(config);

        log.info("新增客户膳食配置成功，ID: {}, 客户ID: {}, 餐类型: {}",
                config.getId(), config.getCustomerId(), config.getMealType());

        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerMealConfig(CustomerMealConfigDTO configDTO) {
        // 验证配置是否存在
        CustomerMealConfig existConfig = customerMealConfigMapper.selectById(configDTO.getId());
        if (existConfig == null) {
            throw new BusinessException("膳食配置不存在");
        }

        // 验证客户是否存在
        validateCustomer(configDTO.getCustomerId());

        // 检查是否已存在相同餐类型的配置（排除自己）
        checkMealConfigConflict(configDTO.getCustomerId(), configDTO.getMealType().name(), configDTO.getId());

        CustomerMealConfig config = new CustomerMealConfig();
        BeanUtils.copyProperties(configDTO, config);

        customerMealConfigMapper.updateById(config);

        log.info("更新客户膳食配置成功，ID: {}, 客户ID: {}, 餐类型: {}",
                config.getId(), config.getCustomerId(), config.getMealType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomerMealConfig(Long id) {
        CustomerMealConfig config = customerMealConfigMapper.selectById(id);
        if (config == null) {
            throw new BusinessException("膳食配置不存在");
        }

        customerMealConfigMapper.deleteById(id);

        log.info("删除客户膳食配置成功，ID: {}, 客户ID: {}, 餐类型: {}",
                id, config.getCustomerId(), config.getMealType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchSetMealConfig(BatchMealConfigDTO batchConfigDTO) {
        // 验证客户是否存在
        validateCustomer(batchConfigDTO.getCustomerId());

        for (CustomerMealConfigDTO configDTO : batchConfigDTO.getMealConfigs()) {
            // 设置客户ID
            configDTO.setCustomerId(batchConfigDTO.getCustomerId());

            // 检查是否已存在相同餐类型的配置
            LambdaQueryWrapper<CustomerMealConfig> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CustomerMealConfig::getCustomerId, configDTO.getCustomerId())
                    .eq(CustomerMealConfig::getMealType, configDTO.getMealType());

            CustomerMealConfig existConfig = customerMealConfigMapper.selectOne(wrapper);

            if (existConfig != null) {
                // 更新现有配置
                CustomerMealConfig updateConfig = new CustomerMealConfig();
                BeanUtils.copyProperties(configDTO, updateConfig);
                updateConfig.setId(existConfig.getId());
                customerMealConfigMapper.updateById(updateConfig);
            } else {
                // 创建新配置
                CustomerMealConfig newConfig = new CustomerMealConfig();
                BeanUtils.copyProperties(configDTO, newConfig);
                newConfig.setStatus(1); // 默认启用
                customerMealConfigMapper.insert(newConfig);
            }
        }

        log.info("批量设置客户膳食配置成功，客户ID: {}, 配置数量: {}",
                batchConfigDTO.getCustomerId(), batchConfigDTO.getMealConfigs().size());
    }

    @Override
    public MealStatisticsDTO getMealStatistics() {
        return customerMealConfigMapper.selectMealStatistics();
    }

    @Override
    public List<CustomerMealConfigDTO> getCustomersWithoutMealConfig(String customerName) {
        return customerMealConfigMapper.selectCustomersWithoutMealConfig(customerName);
    }

    @Override
    public CustomerMealConfigDTO getCustomerMealConfigById(Long id) {
        CustomerMealConfig config = customerMealConfigMapper.selectById(id);
        if (config == null) {
            return null;
        }

        CustomerMealConfigDTO dto = new CustomerMealConfigDTO();
        BeanUtils.copyProperties(config, dto);
        return dto;
    }

    /**
     * 验证客户是否存在且在住
     */
    private void validateCustomer(Long customerId) {
        Customer customer = customerMapper.selectById(customerId);
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }
        if (customer.getStatus() != 1) {
            throw new BusinessException("客户不在住状态");
        }
    }

    /**
     * 检查膳食配置冲突
     */
    private void checkMealConfigConflict(Long customerId, String mealType, Long excludeId) {
        LambdaQueryWrapper<CustomerMealConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerMealConfig::getCustomerId, customerId)
                .eq(CustomerMealConfig::getMealType, mealType);

        if (excludeId != null) {
            wrapper.ne(CustomerMealConfig::getId, excludeId);
        }

        CustomerMealConfig existConfig = customerMealConfigMapper.selectOne(wrapper);
        if (existConfig != null) {
            throw new BusinessException("该客户该餐类型已存在配置");
        }
    }
}
