package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:09
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RemoveServiceCustomerDTO {
    @NotNull(message = "健康管家ID不能为空")
    private Long healthManagerId;

    @NotEmpty(message = "客户ID列表不能为空")
    private List<Long> customerIds;
}
