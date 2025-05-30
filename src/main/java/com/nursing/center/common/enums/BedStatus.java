package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:06
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum BedStatus {
    AVAILABLE("空闲"),
    OCCUPIED("有人"),
    OUT("外出");

    private final String description;

    BedStatus(String description) {
        this.description = description;
    }
}
