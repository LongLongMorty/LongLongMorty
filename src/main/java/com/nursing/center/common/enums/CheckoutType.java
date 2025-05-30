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
public enum CheckoutType {
    NORMAL("正常退住"),
    DEATH("死亡退住"),
    RESERVE("保留床位");

    private final String description;

    CheckoutType(String description) {
        this.description = description;
    }
}
