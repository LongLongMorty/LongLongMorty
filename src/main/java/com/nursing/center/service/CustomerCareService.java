package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:40
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.dto.CustomerCareDTO;
import com.nursing.center.dto.CustomerCareLevelDTO;

import java.util.List;

public interface CustomerCareService {

    /**
     * 查询客户的护理服务列表
     */
    List<CustomerCareDTO> getCustomerCareServices(Long customerId);

    /**
     * 为客户设置护理级别
     */
    void setCustomerCareLevel(CustomerCareLevelDTO customerCareLevelDTO);

    /**
     * 移除客户护理级别
     */
    void removeCustomerCareLevel(Long customerId);

    /**
     * 为客户购买护理项目
     */
    void purchaseCareItems(Long customerId, List<CustomerCareLevelDTO.CustomerCareItemDTO> careItems);

    /**
     * 客户护理服务续费
     */
    void renewCareService(Long customerCareId, Integer additionalQuantity, java.time.LocalDate newExpireDate);

    /**
     * 移除客户护理服务
     */
    void removeCareService(Long customerCareId);

    /**
     * 查询客户未拥有的护理项目
     */
    List<CustomerCareDTO> getAvailableItemsForCustomer(Long customerId, String itemName);
}
