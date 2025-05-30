package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:31
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.ServiceStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CustomerCareDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotNull(message = "护理项目ID不能为空")
    private Long careItemId;

    @NotNull(message = "购买日期不能为空")
    private LocalDate purchaseDate;

    @NotNull(message = "购买数量不能为空")
    @Positive(message = "购买数量必须大于0")
    private Integer purchaseQuantity;

    private Integer usedQuantity;
    private Integer remainingQuantity;

    @NotNull(message = "到期日期不能为空")
    private LocalDate expireDate;

    private ServiceStatus serviceStatus;

    // 关联信息
    private String customerName;
    private String itemName;
    private BigDecimal itemPrice;
    private String itemCode;
}
