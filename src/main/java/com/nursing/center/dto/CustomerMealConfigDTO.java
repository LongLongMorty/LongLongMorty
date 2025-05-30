package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:25
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.MealType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerMealConfigDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotNull(message = "餐类型不能为空")
    private MealType mealType;

    private String specialRequirements;
    private String allergies;
    private String dietaryRestrictions;
    private Integer status;

    // 关联信息
    private String customerName;
    private String bedInfo;
}
