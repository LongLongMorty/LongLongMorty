package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:26
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BatchMealConfigDTO {
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotEmpty(message = "膳食配置不能为空")
    @Valid
    private List<CustomerMealConfigDTO> mealConfigs;
}
