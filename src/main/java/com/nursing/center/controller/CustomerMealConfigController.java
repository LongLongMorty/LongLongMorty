package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:32
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.*;
import com.nursing.center.service.CustomerMealConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "客户膳食配置")
@RestController
@RequestMapping("/api/admin/customer-meal-config")
@RequiredArgsConstructor
@Validated
public class CustomerMealConfigController {

    private final CustomerMealConfigService customerMealConfigService;

    @ApiOperation("分页查询客户膳食配置")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CustomerMealConfigDTO>> getCustomerMealConfigPage(CustomerMealConfigQueryDTO query) {
        IPage<CustomerMealConfigDTO> page = customerMealConfigService.getCustomerMealConfigPage(query);
        return Result.success(page);
    }

    @ApiOperation("查询客户的膳食配置")
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerMealConfigDTO>> getCustomerMealConfigs(@PathVariable Long customerId) {
        List<CustomerMealConfigDTO> configs = customerMealConfigService.getCustomerMealConfigs(customerId);
        return Result.success(configs);
    }

    @ApiOperation("根据ID查询客户膳食配置")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CustomerMealConfigDTO> getCustomerMealConfigById(@PathVariable Long id) {
        CustomerMealConfigDTO config = customerMealConfigService.getCustomerMealConfigById(id);
        return Result.success(config);
    }

    @ApiOperation("添加客户膳食配置")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addCustomerMealConfig(@Valid @RequestBody CustomerMealConfigDTO configDTO) {
        Long id = customerMealConfigService.addCustomerMealConfig(configDTO);
        return Result.success("客户膳食配置添加成功", id);
    }

    @ApiOperation("修改客户膳食配置")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateCustomerMealConfig(@Valid @RequestBody CustomerMealConfigDTO configDTO) {
        customerMealConfigService.updateCustomerMealConfig(configDTO);
        return Result.success("客户膳食配置修改成功");
    }

    @ApiOperation("删除客户膳食配置")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCustomerMealConfig(@PathVariable Long id) {
        customerMealConfigService.deleteCustomerMealConfig(id);
        return Result.success("客户膳食配置删除成功");
    }

    @ApiOperation("批量设置客户膳食配置")
    @PostMapping("/batch")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> batchSetMealConfig(@Valid @RequestBody BatchMealConfigDTO batchConfigDTO) {
        customerMealConfigService.batchSetMealConfig(batchConfigDTO);
        return Result.success("客户膳食配置批量设置成功");
    }

    @ApiOperation("查询膳食统计信息")
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<MealStatisticsDTO> getMealStatistics() {
        MealStatisticsDTO statistics = customerMealConfigService.getMealStatistics();
        return Result.success(statistics);
    }

    @ApiOperation("查询未配置膳食的客户")
    @GetMapping("/customers/without-config")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerMealConfigDTO>> getCustomersWithoutMealConfig(
            @RequestParam(required = false) String customerName) {
        List<CustomerMealConfigDTO> customers = customerMealConfigService.getCustomersWithoutMealConfig(customerName);
        return Result.success(customers);
    }
}
