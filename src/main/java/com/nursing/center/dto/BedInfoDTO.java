package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:08
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.BedStatus;
import com.nursing.center.common.enums.BedType;
import lombok.Data;

@Data
public class BedInfoDTO {
    private Long id;
    private Long roomId;
    private String bedNo;
    private BedType bedType;
    private BedStatus bedStatus;
    private Integer status;

    // 关联信息
    private String roomNo;
    private String buildingNo;
    private Integer floorNo;
    private String customerName; // 如果有人入住
}
