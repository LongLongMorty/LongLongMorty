package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:12
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.dto.BedInfoDTO;
import com.nursing.center.dto.BedStatisticsDTO;

import java.util.List;

public interface BedService {

    /**
     * 获取床位统计信息
     */
    BedStatisticsDTO getBedStatistics();

    /**
     * 根据楼层查询床位信息
     */
    List<BedInfoDTO> getBedsByFloor(Integer floorNo);

    /**
     * 查询房间内的空闲床位
     */
    List<BedInfoDTO> getAvailableBedsByRoom(Long roomId);
}
