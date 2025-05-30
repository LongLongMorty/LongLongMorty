package com.nursing.center.common.enums;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.enums
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  11:30
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("管理员", "ROLE_ADMIN"),
    HEALTH_MANAGER("健康管家", "ROLE_HEALTH_MANAGER");

    private final String description;
    private final String authority;

    UserRole(String description, String authority) {
        this.description = description;
        this.authority = authority;
    }
}
