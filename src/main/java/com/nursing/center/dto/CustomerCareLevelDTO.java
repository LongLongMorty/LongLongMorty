package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:32
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerCareLevelDTO {
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @NotNull(message = "护理级别ID不能为空")
    private Long careLevelId;

    private LocalDate serviceStartDate = LocalDate.now();
    private LocalDate serviceExpireDate; // 默认3个月到期

    // 批量添加的护理项目
    private List<CustomerCareItemDTO> careItems;

    @Data
    public static class CustomerCareItemDTO {
        private Long careItemId;
        private Integer quantity = 1;
        private LocalDate expireDate;
    }
}
