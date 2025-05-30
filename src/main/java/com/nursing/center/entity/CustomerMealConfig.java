package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:23
 * @Description: TODO
 * @Version: 1.0
 */

import com.baomidou.mybatisplus.annotation.TableName;
import com.nursing.center.common.enums.MealType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer_meal_config")
public class CustomerMealConfig extends BaseEntity {
    private Long customerId;
    private MealType mealType;
    private String specialRequirements;
    private String allergies;
    private String dietaryRestrictions;
    private Integer status; // 0-停用 1-启用
}
