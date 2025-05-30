package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:30
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class CareLevelDTO {
    private Long id;

    @NotBlank(message = "护理级别名称不能为空")
    private String levelName;

    @NotBlank(message = "护理级别编码不能为空")
    private String levelCode;

    private String description;
    private Integer status;

    // 关联的护理项目列表
    private List<CareItemDTO> careItems;
}
