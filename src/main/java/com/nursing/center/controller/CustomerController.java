package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:12
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.CustomerDTO;
import com.nursing.center.dto.CustomerQueryDTO;
import com.nursing.center.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "客户管理")
@RestController
@RequestMapping("/api/admin/customer")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @ApiOperation("分页查询客户信息")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CustomerDTO>> getCustomerPage(CustomerQueryDTO query) {
        IPage<CustomerDTO> page = customerService.getCustomerPage(query);
        return Result.success(page);
    }

    @ApiOperation("根据ID查询客户信息")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CustomerDTO> getCustomerById(@PathVariable Long id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return Result.success(customer);
    }

    @ApiOperation("添加客户")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Long customerId = customerService.addCustomer(customerDTO);
        return Result.success("客户添加成功", customerId);
    }

    @ApiOperation("修改客户信息")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO);
        return Result.success("客户信息修改成功");
    }

    @ApiOperation("删除客户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return Result.success("客户删除成功");
    }
}
