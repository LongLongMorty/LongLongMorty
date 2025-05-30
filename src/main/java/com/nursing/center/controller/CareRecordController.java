package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:45
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.CareRecordDTO;
import com.nursing.center.dto.CareRecordQueryDTO;
import com.nursing.center.service.CareRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "护理记录管理")
@RestController
@RequestMapping("/api/admin/care-record")
@RequiredArgsConstructor
@Validated
public class CareRecordController {

    private final CareRecordService careRecordService;

    @ApiOperation("分页查询护理记录")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CareRecordDTO>> getCareRecordPage(CareRecordQueryDTO query) {
        IPage<CareRecordDTO> page = careRecordService.getCareRecordPage(query);
        return Result.success(page);
    }

    @ApiOperation("查询客户的护理记录")
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CareRecordDTO>> getCustomerCareRecords(@PathVariable Long customerId,
                                                               @RequestParam(defaultValue = "1") Integer pageNum,
                                                               @RequestParam(defaultValue = "10") Integer pageSize) {
        IPage<CareRecordDTO> page = careRecordService.getCustomerCareRecords(customerId, pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation("添加护理记录")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addCareRecord(@Valid @RequestBody CareRecordDTO careRecordDTO) {
        Long recordId = careRecordService.addCareRecord(careRecordDTO);
        return Result.success("护理记录添加成功", recordId);
    }

    @ApiOperation("删除护理记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCareRecord(@PathVariable Long id) {
        careRecordService.deleteCareRecord(id);
        return Result.success("护理记录删除成功");
    }

    @ApiOperation("根据ID查询护理记录")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CareRecordDTO> getCareRecordById(@PathVariable Long id) {
        CareRecordDTO record = careRecordService.getCareRecordById(id);
        return Result.success(record);
    }
}
