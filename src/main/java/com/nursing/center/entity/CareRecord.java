package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:29
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("care_record")
public class CareRecord extends BaseEntity {
    private Long customerId;
    private Long careItemId;
    private Long healthManagerId;
    private LocalDateTime careTime;
    private Integer careQuantity;
    private String careContent;
    private String remark;
}
