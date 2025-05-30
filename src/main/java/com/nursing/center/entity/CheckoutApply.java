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
import com.nursing.center.common.enums.CheckoutType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("checkout_apply")
public class CheckoutApply extends BaseEntity {
    private Long customerId;
    private Long applicantId;
    private CheckoutType checkoutType;
    private String checkoutReason;
    private LocalDate checkoutDate;
    private ApplyStatus applyStatus;
    private Long approverId;
    private LocalDateTime approveTime;
    private String approveRemark;
}
