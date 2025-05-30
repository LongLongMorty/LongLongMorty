package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:01
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.common.utils.SecurityUtils;
import com.nursing.center.dto.*;
import com.nursing.center.service.CheckoutApplyService;
import com.nursing.center.service.OutingApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "健康管家申请管理")
@RestController
@RequestMapping("/api/health-manager/apply")
@RequiredArgsConstructor
@Validated
public class HealthManagerApplyController {

    private final CheckoutApplyService checkoutApplyService;
    private final OutingApplyService outingApplyService;

    // ==================== 退住申请 ====================

    @ApiOperation("提交退住申请")
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<Long> submitCheckoutApply(@Valid @RequestBody CheckoutApplyDTO checkoutApplyDTO) {
        // 设置申请人为当前健康管家
        checkoutApplyDTO.setApplicantId(SecurityUtils.getCurrentUserId());
        Long applyId = checkoutApplyService.submitCheckoutApply(checkoutApplyDTO);
        return Result.success("退住申请提交成功", applyId);
    }

    @ApiOperation("查询自己提交的退住申请")
    @GetMapping("/checkout/my")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<IPage<CheckoutApplyDTO>> getMyCheckoutApplies(CheckoutApplyQueryDTO query) {
        // 只查询当前健康管家提交的申请
        query.setApplicantId(SecurityUtils.getCurrentUserId());
        IPage<CheckoutApplyDTO> page = checkoutApplyService.getCheckoutApplyPage(query);
        return Result.success(page);
    }

    // ==================== 外出申请 ====================

    @ApiOperation("提交外出申请")
    @PostMapping("/outing")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<Long> submitOutingApply(@Valid @RequestBody OutingApplyDTO outingApplyDTO) {
        // 设置申请人为当前健康管家
        outingApplyDTO.setApplicantId(SecurityUtils.getCurrentUserId());
        Long applyId = outingApplyService.submitOutingApply(outingApplyDTO);
        return Result.success("外出申请提交成功", applyId);
    }

    @ApiOperation("查询自己提交的外出申请")
    @GetMapping("/outing/my")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<IPage<OutingApplyDTO>> getMyOutingApplies(OutingApplyQueryDTO query) {
        // 只查询当前健康管家提交的申请
        query.setApplicantId(SecurityUtils.getCurrentUserId());
        IPage<OutingApplyDTO> page = outingApplyService.getOutingApplyPage(query);
        return Result.success(page);
    }

    @ApiOperation("登记客户回院")
    @PostMapping("/outing/return")
    @PreAuthorize("hasRole('HEALTH_MANAGER')")
    public Result<String> registerReturn(@Valid @RequestBody OutingReturnDTO returnDTO) {
        outingApplyService.registerReturn(returnDTO);
        return Result.success("回院登记完成");
    }
}
