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
public enum BedType {
    STANDARD("标准床"),
    CARE("护理床");

    private final String description;

    BedType(String description) {
        this.description = description;
    }
}
