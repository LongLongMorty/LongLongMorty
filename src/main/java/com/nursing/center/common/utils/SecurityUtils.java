package com.nursing.center.common.utils;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.utils
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  12:56
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * 获取当前登录用户
     */
    public static UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        }
        return null;
    }

    /**
     * 获取当前用户ID
     */
    public static Long getCurrentUserId() {
        UserPrincipal user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    /**
     * 获取当前用户名
     */
    public static String getCurrentUsername() {
        UserPrincipal user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    /**
     * 判断当前用户是否为管理员
     */
    public static boolean isAdmin() {
        UserPrincipal user = getCurrentUser();
        return user != null && "ADMIN".equals(user.getRole().name());
    }

    /**
     * 判断当前用户是否为健康管家
     */
    public static boolean isHealthManager() {
        UserPrincipal user = getCurrentUser();
        return user != null && "HEALTH_MANAGER".equals(user.getRole().name());
    }
}
