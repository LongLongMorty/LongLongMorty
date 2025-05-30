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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class MealCalendarDTO {
    private Long id;

    @NotNull(message = "膳食日期不能为空")
    private LocalDate mealDate;

    @NotNull(message = "餐类型不能为空")
    private MealType mealType;

    @NotBlank(message = "膳食名称不能为空")
    private String mealName;

    private String mealCategory;
    private String description;
    private Integer status;
}
