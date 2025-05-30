package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  12:54
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.common.utils.JwtUtils;
import com.nursing.center.dto.LoginRequest;
import com.nursing.center.dto.LoginResponse;
import com.nursing.center.security.UserPrincipal;
import com.nursing.center.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            // 进行认证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // 获取用户信息
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            // 生成JWT token
            String token = jwtUtils.generateToken(
                    userPrincipal.getUsername(),
                    userPrincipal.getRole().name(),
                    userPrincipal.getId()
            );

            log.info("用户登录成功: {}", loginRequest.getUsername());

            return new LoginResponse(
                    token,
                    userPrincipal.getId(),
                    userPrincipal.getUsername(),
                    userPrincipal.getRealName(),
                    userPrincipal.getRole(),
                    jwtExpiration
            );

        } catch (AuthenticationException e) {
            log.error("用户登录失败: {}, 原因: {}", loginRequest.getUsername(), e.getMessage());
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public LoginResponse refreshToken(String authHeader) {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException("无效的Authorization头");
        }

        String oldToken = authHeader.substring(7);
        String newToken = jwtUtils.refreshToken(oldToken);

        if (newToken == null) {
            throw new BusinessException("token刷新失败");
        }

        String username = jwtUtils.getUsernameFromToken(newToken);
        String role = jwtUtils.getRoleFromToken(newToken);
        Long userId = jwtUtils.getUserIdFromToken(newToken);

        log.info("token刷新成功: {}", username);

        return new LoginResponse(
                newToken,
                userId,
                username,
                null, // 刷新时不返回realName
                null, // 刷新时不返回role对象
                jwtExpiration
        );
    }
}
