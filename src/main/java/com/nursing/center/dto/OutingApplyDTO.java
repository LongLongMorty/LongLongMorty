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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OutingApplyDTO {
    private Long id;

    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    private Long applicantId; // 申请人ID，健康管家申请时使用

    @NotBlank(message = "外出事由不能为空")
    private String outingReason;

    @NotNull(message = "外出时间不能为空")
    private LocalDateTime outingDate;

    @NotNull(message = "预计回院时间不能为空")
    private LocalDateTime expectedReturnDate;

    private LocalDateTime actualReturnDate;
    private ApplyStatus applyStatus;
    private Long approverId;
    private LocalDateTime approveTime;
    private String approveRemark;

    // 关联信息
    private String customerName;
    private String applicantName;
    private String approverName;
    private String bedInfo; // 床位信息（楼栋-房间-床位）
}
