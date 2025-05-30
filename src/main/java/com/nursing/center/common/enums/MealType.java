package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:24
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum MealType {
    BREAKFAST("早餐"),
    LUNCH("午餐"),
    DINNER("晚餐"),
    SNACK("加餐");

    private final String description;

    MealType(String description) {
        this.description = description;
    }
}
