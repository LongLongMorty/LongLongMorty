package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:08
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.CustomerType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerServiceDTO {
    private Long id;
    private Long customerId;
    private Long healthManagerId;
    private String customerName;
    private Integer age;
    private String idCard;
    private CustomerType customerType;
    private String bedInfo; // 床位信息
    private LocalDate checkInDate;
    private LocalDate contractExpireDate;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private Integer serviceStatus; // 服务状态
    private Integer customerStatus; // 客户状态

    // 护理信息
    private String careLevelName;
    private Integer totalCareItems; // 护理项目总数
    private Integer activeCareItems; // 有效护理项目数
}
