package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:49
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.annotation.TableName;
import com.nursing.center.common.enums.ApplyStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("outing_apply")
public class OutingApply extends BaseEntity {
    private Long customerId;
    private Long applicantId;
    private String outingReason;
    private LocalDateTime outingDate;
    private LocalDateTime expectedReturnDate;
    private LocalDateTime actualReturnDate;
    private ApplyStatus applyStatus;
    private Long approverId;
    private LocalDateTime approveTime;
    private String approveRemark;
}
