package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:54
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.ApplyStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CheckoutApproveDTO {
    @NotNull(message = "申请ID不能为空")
    private Long applyId;

    @NotNull(message = "审批状态不能为空")
    private ApplyStatus applyStatus;

    private String approveRemark;
}
