package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:12
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.dto.BedInfoDTO;
import com.nursing.center.dto.BedStatisticsDTO;
import com.nursing.center.mapper.BedMapper;
import com.nursing.center.service.BedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BedServiceImpl implements BedService {

    private final BedMapper bedMapper;

    @Override
    public BedStatisticsDTO getBedStatistics() {
        return bedMapper.selectBedStatistics();
    }

    @Override
    public List<BedInfoDTO> getBedsByFloor(Integer floorNo) {
        return bedMapper.selectBedsByFloor(floorNo);
    }

    @Override
    public List<BedInfoDTO> getAvailableBedsByRoom(Long roomId) {
        return bedMapper.selectAvailableBedsByRoom(roomId);
    }
}
