package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  12:51
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.UserRole;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;
    private String realName;
    private UserRole role;
    private Long expiresIn;

    public LoginResponse(String token, Long userId, String username, String realName, UserRole role, Long expiresIn) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.role = role;
        this.expiresIn = expiresIn;
    }
}
