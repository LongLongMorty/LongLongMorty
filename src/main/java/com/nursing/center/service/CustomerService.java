package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:11
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.CustomerDTO;
import com.nursing.center.dto.CustomerQueryDTO;

public interface CustomerService {

    /**
     * 分页查询客户信息
     */
    IPage<CustomerDTO> getCustomerPage(CustomerQueryDTO query);

    /**
     * 根据ID查询客户信息
     */
    CustomerDTO getCustomerById(Long id);

    /**
     * 添加客户
     */
    Long addCustomer(CustomerDTO customerDTO);

    /**
     * 修改客户信息
     */
    void updateCustomer(CustomerDTO customerDTO);

    /**
     * 删除客户（逻辑删除）
     */
    void deleteCustomer(Long id);
}
