package com.nursing.center.common.exception;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.common.exception
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  12:55
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
