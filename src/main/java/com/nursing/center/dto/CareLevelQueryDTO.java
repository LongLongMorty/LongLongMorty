package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:31
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

@Data
public class CareLevelQueryDTO {
    private Integer status;
    private String levelName; // 模糊查询
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
