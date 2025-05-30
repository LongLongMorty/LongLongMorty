package com.nursing.center.security;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.security
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  11:49
 * @Description: TODO
 * @Version: 1.0
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nursing.center.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        log.error("访问被拒绝: {}", accessDeniedException.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Result<Object> result = Result.error(403, "权限不足，访问被拒绝");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
