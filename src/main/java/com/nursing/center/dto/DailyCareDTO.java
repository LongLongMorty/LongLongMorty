package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:10
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Data
public class DailyCareDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotNull(message = "护理项目ID不能为空")
    private Long careItemId;

    private Long healthManagerId; // 自动设置为当前健康管家

    @NotNull(message = "护理数量不能为空")
    @Positive(message = "护理数量必须大于0")
    private Integer careQuantity;

    private LocalDateTime careTime = LocalDateTime.now();
    private String careContent;
    private String remark;

    // 关联信息
    private String customerName;
    private String itemName;
    private String itemCode;
    private Integer remainingQuantity; // 剩余可用数量
}
