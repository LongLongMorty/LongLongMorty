package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:11
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.ServiceStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerCareItemDTO {
    private Long customerCareId;
    private Long careItemId;
    private String itemCode;
    private String itemName;
    private BigDecimal price;
    private Integer executeCycle;
    private Integer executeTimes;
    private String description;

    // 服务信息
    private Integer purchaseQuantity;
    private Integer usedQuantity;
    private Integer remainingQuantity;
    private LocalDate purchaseDate;
    private LocalDate expireDate;
    private ServiceStatus serviceStatus;

    // 可护理标识
    private Boolean canCare; // 是否可以进行护理
    private String statusDesc; // 状态描述
}
