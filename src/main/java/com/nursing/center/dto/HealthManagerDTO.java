package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:07
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class HealthManagerDTO {
    private Long id;
    private String username;
    private String realName;
    private String phone;
    private UserRole role;
    private Integer status;

    // 服务的客户列表
    private List<CustomerServiceDTO> serviceCustomers;

    // 统计信息
    private Integer totalCustomers; // 服务客户总数
    private Integer activeCustomers; // 在住客户数
}
