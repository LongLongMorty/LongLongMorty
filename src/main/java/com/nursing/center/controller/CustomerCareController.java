package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:44
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.CustomerCareDTO;
import com.nursing.center.dto.CustomerCareLevelDTO;
import com.nursing.center.service.CustomerCareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "客户护理设置")
@RestController
@RequestMapping("/api/admin/customer-care")
@RequiredArgsConstructor
@Validated
public class CustomerCareController {

    private final CustomerCareService customerCareService;

    @ApiOperation("查询客户的护理服务列表")
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerCareDTO>> getCustomerCareServices(@PathVariable Long customerId) {
        List<CustomerCareDTO> services = customerCareService.getCustomerCareServices(customerId);
        return Result.success(services);
    }

    @ApiOperation("为客户设置护理级别")
    @PostMapping("/set-level")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> setCustomerCareLevel(@Valid @RequestBody CustomerCareLevelDTO customerCareLevelDTO) {
        customerCareService.setCustomerCareLevel(customerCareLevelDTO);
        return Result.success("护理级别设置成功");
    }

    @ApiOperation("移除客户护理级别")
    @DeleteMapping("/remove-level/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> removeCustomerCareLevel(@PathVariable Long customerId) {
        customerCareService.removeCustomerCareLevel(customerId);
        return Result.success("护理级别移除成功");
    }

    @ApiOperation("为客户购买护理项目")
    @PostMapping("/purchase/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> purchaseCareItems(@PathVariable Long customerId,
                                            @RequestBody List<CustomerCareLevelDTO.CustomerCareItemDTO> careItems) {
        customerCareService.purchaseCareItems(customerId, careItems);
        return Result.success("护理项目购买成功");
    }

    @ApiOperation("护理服务续费")
    @PostMapping("/renew/{customerCareId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> renewCareService(@PathVariable Long customerCareId,
                                           @RequestParam Integer additionalQuantity,
                                           @RequestParam LocalDate newExpireDate) {
        customerCareService.renewCareService(customerCareId, additionalQuantity, newExpireDate);
        return Result.success("护理服务续费成功");
    }

    @ApiOperation("移除护理服务")
    @DeleteMapping("/{customerCareId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> removeCareService(@PathVariable Long customerCareId) {
        customerCareService.removeCareService(customerCareId);
        return Result.success("护理服务移除成功");
    }

    @ApiOperation("查询客户未拥有的护理项目")
    @GetMapping("/available/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CustomerCareDTO>> getAvailableItemsForCustomer(@PathVariable Long customerId,
                                                                      @RequestParam(required = false) String itemName) {
        List<CustomerCareDTO> items = customerCareService.getAvailableItemsForCustomer(customerId, itemName);
        return Result.success(items);
    }
}
