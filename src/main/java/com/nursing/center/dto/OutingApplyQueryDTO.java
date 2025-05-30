package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:55
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.ApplyStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OutingApplyQueryDTO {
    private String customerName; // 模糊查询
    private ApplyStatus applyStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long applicantId; // 查询某个申请人的申请
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
