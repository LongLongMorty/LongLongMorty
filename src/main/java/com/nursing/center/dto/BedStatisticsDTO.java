package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:08
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

@Data
public class BedStatisticsDTO {
    private Integer totalBeds;
    private Integer availableBeds;
    private Integer occupiedBeds;
    private Integer outBeds;
}
