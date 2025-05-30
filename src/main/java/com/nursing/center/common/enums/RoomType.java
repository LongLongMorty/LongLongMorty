package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:07
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum RoomType {
    STANDARD("标准间"),
    VIP("贵宾间");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }
}
