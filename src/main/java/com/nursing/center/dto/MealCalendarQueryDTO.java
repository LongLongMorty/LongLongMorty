package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:24
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.MealType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MealCalendarQueryDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private MealType mealType;
    private Integer status;
    private String mealName; // 模糊查询
    private String mealCategory;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
