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
import com.nursing.center.dto.CheckoutApplyDTO;
import com.nursing.center.dto.CheckoutApplyQueryDTO;
import com.nursing.center.dto.CheckoutApproveDTO;
import com.nursing.center.service.CheckoutApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "退住申请管理")
@RestController
@RequestMapping("/api/admin/checkout-apply")
@RequiredArgsConstructor
@Validated
public class CheckoutApplyController {

    private final CheckoutApplyService checkoutApplyService;

    @ApiOperation("分页查询退住申请")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CheckoutApplyDTO>> getCheckoutApplyPage(CheckoutApplyQueryDTO query) {
        IPage<CheckoutApplyDTO> page = checkoutApplyService.getCheckoutApplyPage(query);
        return Result.success(page);
    }

    @ApiOperation("根据ID查询退住申请详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CheckoutApplyDTO> getCheckoutApplyById(@PathVariable Long id) {
        CheckoutApplyDTO apply = checkoutApplyService.getCheckoutApplyById(id);
        return Result.success(apply);
    }

    @ApiOperation("审批退住申请")
    @PostMapping("/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> approveCheckoutApply(@Valid @RequestBody CheckoutApproveDTO approveDTO) {
        checkoutApplyService.approveCheckoutApply(approveDTO);
        return Result.success("退住申请审批完成");
    }

    @ApiOperation("删除退住申请")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCheckoutApply(@PathVariable Long id) {
        checkoutApplyService.deleteCheckoutApply(id);
        return Result.success("退住申请删除成功");
    }
}
