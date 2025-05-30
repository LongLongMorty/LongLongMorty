package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:00
 * @Description: TODO
 * @Version: 1.0
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.OutingApplyDTO;
import com.nursing.center.dto.OutingApplyQueryDTO;
import com.nursing.center.dto.OutingApproveDTO;
import com.nursing.center.dto.OutingReturnDTO;
import com.nursing.center.service.OutingApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "外出申请管理")
@RestController
@RequestMapping("/api/admin/outing-apply")
@RequiredArgsConstructor
@Validated
public class OutingApplyController {

    private final OutingApplyService outingApplyService;

    @ApiOperation("分页查询外出申请")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<OutingApplyDTO>> getOutingApplyPage(OutingApplyQueryDTO query) {
        IPage<OutingApplyDTO> page = outingApplyService.getOutingApplyPage(query);
        return Result.success(page);
    }

    @ApiOperation("根据ID查询外出申请详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<OutingApplyDTO> getOutingApplyById(@PathVariable Long id) {
        OutingApplyDTO apply = outingApplyService.getOutingApplyById(id);
        return Result.success(apply);
    }

    @ApiOperation("审批外出申请")
    @PostMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> approveOutingApply(@Valid @RequestBody OutingApproveDTO approveDTO) {
        outingApplyService.approveOutingApply(approveDTO);
        return Result.success("外出申请审批完成");
    }

    @ApiOperation("登记回院")
    @PostMapping("/return")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> registerReturn(@Valid @RequestBody OutingReturnDTO returnDTO) {
        outingApplyService.registerReturn(returnDTO);
        return Result.success("回院登记完成");
    }

    @ApiOperation("删除外出申请")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteOutingApply(@PathVariable Long id) {
        outingApplyService.deleteOutingApply(id);
        return Result.success("外出申请删除成功");
    }
}
