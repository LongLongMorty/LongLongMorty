package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:55
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class OutingReturnDTO {
    @NotNull(message = "申请ID不能为空")
    private Long applyId;

    @NotNull(message = "实际回院时间不能为空")
    private LocalDateTime actualReturnDate;
}
