package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:50
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum ApplyStatus {
    SUBMITTED("已提交"),
    APPROVED("通过"),
    REJECTED("不通过");

    private final String description;

    ApplyStatus(String description) {
        this.description = description;
    }
}
