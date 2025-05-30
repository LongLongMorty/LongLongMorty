package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:13
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.*;
import com.nursing.center.service.HealthManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "健康管家管理")
@RestController
@RequestMapping("/api/admin/health-manager")
@RequiredArgsConstructor
@Validated
public class HealthManagerController {

    private final HealthManagerService healthManagerService;

    @ApiOperation("分页查询健康管家")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<HealthManagerDTO>> getHealthManagerPage(HealthManagerQueryDTO query) {
        IPage<HealthManagerDTO> page = healthManagerService.getHealthManagerPage(query);
        return Result.success(page);
    }

    @ApiOperation("查询健康管家的服务客户列表")
    @GetMapping("/{id}/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerServiceDTO>> getServiceCustomers(@PathVariable Long id) {
        List<CustomerServiceDTO> customers = healthManagerService.getServiceCustomers(id);
        return Result.success(customers);
    }

    @ApiOperation("查询无健康管家的客户")
    @GetMapping("/customers/without-manager")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerServiceDTO>> getCustomersWithoutManager(@RequestParam(required = false) String customerName) {
        List<CustomerServiceDTO> customers = healthManagerService.getCustomersWithoutManager(customerName);
        return Result.success(customers);
    }

    @ApiOperation("为健康管家设置服务客户")
    @PostMapping("/set-customers")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> setServiceCustomers(@Valid @RequestBody SetServiceCustomerDTO setServiceDTO) {
        healthManagerService.setServiceCustomers(setServiceDTO);
        return Result.success("服务客户设置成功");
    }

    @ApiOperation("移除健康管家的服务客户")
    @PostMapping("/remove-customers")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> removeServiceCustomers(@Valid @RequestBody RemoveServiceCustomerDTO removeServiceDTO) {
        healthManagerService.removeServiceCustomers(removeServiceDTO);
        return Result.success("服务客户移除成功");
    }
}
