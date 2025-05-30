package com.nursing.center.controller;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.controller
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:42
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.common.result.Result;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.dto.CareItemQueryDTO;
import com.nursing.center.service.CareItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "护理项目管理")
@RestController
@RequestMapping("/api/admin/care-item")
@RequiredArgsConstructor
@Validated
public class CareItemController {

    private final CareItemService careItemService;

    @ApiOperation("分页查询护理项目")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CareItemDTO>> getCareItemPage(CareItemQueryDTO query) {
        IPage<CareItemDTO> page = careItemService.getCareItemPage(query);
        return Result.success(page);
    }

    @ApiOperation("根据ID查询护理项目")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CareItemDTO> getCareItemById(@PathVariable Long id) {
        CareItemDTO careItem = careItemService.getCareItemById(id);
        return Result.success(careItem);
    }

    @ApiOperation("添加护理项目")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addCareItem(@Valid @RequestBody CareItemDTO careItemDTO) {
        Long careItemId = careItemService.addCareItem(careItemDTO);
        return Result.success("护理项目添加成功", careItemId);
    }

    @ApiOperation("修改护理项目")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateCareItem(@Valid @RequestBody CareItemDTO careItemDTO) {
        careItemService.updateCareItem(careItemDTO);
        return Result.success("护理项目修改成功");
    }

    @ApiOperation("删除护理项目")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCareItem(@PathVariable Long id) {
        careItemService.deleteCareItem(id);
        return Result.success("护理项目删除成功");
    }

    @ApiOperation("查询启用状态的护理项目列表")
    @GetMapping("/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CareItemDTO>> getEnabledCareItems() {
        List<CareItemDTO> items = careItemService.getEnabledCareItems();
        return Result.success(items);
    }
}
