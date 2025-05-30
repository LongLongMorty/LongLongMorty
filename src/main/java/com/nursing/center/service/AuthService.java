package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  12:53
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.dto.LoginRequest;
import com.nursing.center.dto.LoginResponse;

public interface AuthService {

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 刷新token
     */
    LoginResponse refreshToken(String authHeader);
}
