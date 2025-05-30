package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:31
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.*;

import java.util.List;

public interface CustomerMealConfigService {

    /**
     * 分页查询客户膳食配置
     */
    IPage<CustomerMealConfigDTO> getCustomerMealConfigPage(CustomerMealConfigQueryDTO query);

    /**
     * 查询客户的膳食配置
     */
    List<CustomerMealConfigDTO> getCustomerMealConfigs(Long customerId);

    /**
     * 添加客户膳食配置
     */
    Long addCustomerMealConfig(CustomerMealConfigDTO configDTO);

    /**
     * 修改客户膳食配置
     */
    void updateCustomerMealConfig(CustomerMealConfigDTO configDTO);

    /**
     * 删除客户膳食配置
     */
    void deleteCustomerMealConfig(Long id);

    /**
     * 批量设置客户膳食配置
     */
    void batchSetMealConfig(BatchMealConfigDTO batchConfigDTO);

    /**
     * 查询膳食统计信息
     */
    MealStatisticsDTO getMealStatistics();

    /**
     * 查询未配置膳食的客户
     */
    List<CustomerMealConfigDTO> getCustomersWithoutMealConfig(String customerName);

    /**
     * 根据ID查询客户膳食配置
     */
    CustomerMealConfigDTO getCustomerMealConfigById(Long id);
}
