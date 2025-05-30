package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:10
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nursing.center.dto.BedInfoDTO;
import com.nursing.center.dto.BedStatisticsDTO;
import com.nursing.center.entity.Bed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BedMapper extends BaseMapper<Bed> {

    /**
     * 查询床位统计信息
     */
    BedStatisticsDTO selectBedStatistics();

    /**
     * 根据楼层查询床位信息
     */
    List<BedInfoDTO> selectBedsByFloor(@Param("floorNo") Integer floorNo);

    /**
     * 查询房间内的空闲床位
     */
    List<BedInfoDTO> selectAvailableBedsByRoom(@Param("roomId") Long roomId);
}
