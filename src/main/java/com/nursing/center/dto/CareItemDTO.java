package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:09
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class CareItemDTO {
    private Long id;

    @NotBlank(message = "项目编号不能为空")
    private String itemCode;

    @NotBlank(message = "项目名称不能为空")
    private String itemName;

    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "执行周期不能为空")
    @Positive(message = "执行周期必须大于0")
    private Integer executeCycle;

    @NotNull(message = "执行次数不能为空")
    @Positive(message = "执行次数必须大于0")
    private Integer executeTimes;

    private String description;
    private Integer status;
}
