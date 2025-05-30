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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class WeeklyMealPlanDTO {
    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @NotEmpty(message = "膳食计划不能为空")
    @Valid
    private List<MealCalendarDTO> mealPlans;
}
