package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:30
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum ServiceStatus {
    NORMAL("正常"),
    EXPIRED("到期"),
    ARREARS("欠费"),
    USED_UP("用完");

    private final String description;

    ServiceStatus(String description) {
        this.description = description;
    }
}
