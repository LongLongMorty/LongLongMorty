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
import com.nursing.center.common.enums.BedStatus;
import com.nursing.center.common.enums.BedType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bed")
public class Bed extends BaseEntity {
    private Long roomId;
    private String bedNo;
    private BedType bedType;
    private BedStatus bedStatus;
    private Integer status;
}
