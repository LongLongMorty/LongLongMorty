package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:04
 * @Description: TODO
 * @Version: 1.0
 */

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("care_level")
public class CareLevel extends BaseEntity {
    private String levelName;
    private String levelCode;
    private String description;
    private Integer status;
}
