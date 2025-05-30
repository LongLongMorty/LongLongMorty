package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:18
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.annotation.TableName;
import com.nursing.center.common.enums.ServiceStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer_care")
public class CustomerCare extends BaseEntity {
    private Long customerId;
    private Long careItemId;
    private LocalDate purchaseDate;
    private Integer purchaseQuantity;
    private Integer usedQuantity;
    private Integer remainingQuantity;
    private LocalDate expireDate;
    private ServiceStatus serviceStatus;
}
