package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:26
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.MealType;
import lombok.Data;

@Data
public class CustomerMealConfigQueryDTO {
    private Long customerId;
    private String customerName; // 模糊查询
    private MealType mealType;
    private Integer status;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
