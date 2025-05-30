package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:13
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.BedInfoDTO;
import com.nursing.center.dto.BedStatisticsDTO;
import com.nursing.center.service.BedService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "床位管理")
@RestController
@RequestMapping("/api/admin/bed")
@RequiredArgsConstructor
public class BedController {

    private final BedService bedService;

    @ApiOperation("获取床位统计信息")
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<BedStatisticsDTO> getBedStatistics() {
        BedStatisticsDTO statistics = bedService.getBedStatistics();
        return Result.success(statistics);
    }

    @ApiOperation("根据楼层查询床位信息")
    @GetMapping("/floor/{floorNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<BedInfoDTO>> getBedsByFloor(@PathVariable Integer floorNo) {
        List<BedInfoDTO> beds = bedService.getBedsByFloor(floorNo);
        return Result.success(beds);
    }

    @ApiOperation("查询房间内的空闲床位")
    @GetMapping("/available/room/{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<BedInfoDTO>> getAvailableBedsByRoom(@PathVariable Long roomId) {
        List<BedInfoDTO> beds = bedService.getAvailableBedsByRoom(roomId);
        return Result.success(beds);
    }
}