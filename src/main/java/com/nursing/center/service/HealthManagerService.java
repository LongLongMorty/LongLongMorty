package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:12
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.*;

import java.util.List;

public interface HealthManagerService {

    /**
     * 分页查询健康管家
     */
    IPage<HealthManagerDTO> getHealthManagerPage(HealthManagerQueryDTO query);

    /**
     * 查询健康管家的服务客户列表
     */
    List<CustomerServiceDTO> getServiceCustomers(Long healthManagerId);

    /**
     * 查询无健康管家的客户
     */
    List<CustomerServiceDTO> getCustomersWithoutManager(String customerName);

    /**
     * 为健康管家设置服务客户
     */
    void setServiceCustomers(SetServiceCustomerDTO setServiceDTO);

    /**
     * 移除健康管家的服务客户
     */
    void removeServiceCustomers(RemoveServiceCustomerDTO removeServiceDTO);

    /**
     * 查询客户的护理项目列表
     */
    List<CustomerCareItemDTO> getCustomerCareItems(Long customerId);

    /**
     * 进行日常护理
     */
    Long performDailyCare(DailyCareDTO dailyCareDTO);

    /**
     * 查询健康管家服务的客户列表（当前登录健康管家）
     */
    List<CustomerServiceDTO> getMyServiceCustomers(String customerName);
}
