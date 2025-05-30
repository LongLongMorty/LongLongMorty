package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:05
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum Gender {
    MALE("男"),
    FEMALE("女");

    private final String description;

    Gender(String description) {
        this.description = description;
    }
}
