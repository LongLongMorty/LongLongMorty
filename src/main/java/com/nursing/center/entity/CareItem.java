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

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("care_item")
public class CareItem extends BaseEntity {
    private String itemCode;
    private String itemName;
    private BigDecimal price;
    private Integer executeCycle;
    private Integer executeTimes;
    private String description;
    private Integer status;
}
