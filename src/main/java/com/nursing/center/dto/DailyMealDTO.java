package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:25
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyMealDTO {
    private LocalDate mealDate;
    private List<MealCalendarDTO> meals;
}
