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
public enum CustomerType {
    SELF_CARE("自理老人"),
    CARE("护理老人");

    private final String description;

    CustomerType(String description) {
        this.description = description;
    }
}
