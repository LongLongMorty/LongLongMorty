package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:14
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.*;
import com.nursing.center.service.CareRecordService;
import com.nursing.center.service.HealthManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "健康管家日常护理")
@RestController
@RequestMapping("/api/health-manager/daily-care")
@RequiredArgsConstructor
@Validated
public class DailyCareController {

    private final HealthManagerService healthManagerService;
    private final CareRecordService careRecordService;

    @ApiOperation("查询我服务的客户列表")
    @GetMapping("/my-customers")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<List<CustomerServiceDTO>> getMyServiceCustomers(@RequestParam(required = false) String customerName) {
        List<CustomerServiceDTO> customers = healthManagerService.getMyServiceCustomers(customerName);
        return Result.success(customers);
    }

    @ApiOperation("查询客户的护理项目列表")
    @GetMapping("/customer/{customerId}/care-items")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<List<CustomerCareItemDTO>> getCustomerCareItems(@PathVariable Long customerId) {
        List<CustomerCareItemDTO> items = healthManagerService.getCustomerCareItems(customerId);
        return Result.success(items);
    }

    @ApiOperation("进行日常护理")
    @PostMapping("/perform")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<Long> performDailyCare(@Valid @RequestBody DailyCareDTO dailyCareDTO) {
        Long recordId = healthManagerService.performDailyCare(dailyCareDTO);
        return Result.success("护理完成", recordId);
    }

    @ApiOperation("查询客户的护理记录")
    @GetMapping("/customer/{customerId}/records")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<IPage<CareRecordDTO>> getCustomerCareRecords(@PathVariable Long customerId,
                                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<CareRecordDTO> page = careRecordService.getCustomerCareRecords(customerId, pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("删除护理记录")
    @DeleteMapping("/record/{recordId}")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<String> deleteCareRecord(@PathVariable Long recordId) {
        careRecordService.deleteCareRecord(recordId);
        return Result.success("护理记录删除成功");
    }
}
