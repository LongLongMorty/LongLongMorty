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
import com.nursing.center.dto.CareLevelDTO;
import com.nursing.center.dto.CareLevelQueryDTO;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.service.CareLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "护理级别管理")
@RestController
@RequestMapping("/api/admin/care-level")
@RequiredArgsConstructor
@Validated
public class CareLevelController {

    private final CareLevelService careLevelService;

    @ApiOperation("分页查询护理级别")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<CareLevelDTO>> getCareLevelPage(CareLevelQueryDTO query) {
        IPage<CareLevelDTO> page = careLevelService.getCareLevelPage(query);
        return Result.success(page);
    }

    @ApiOperation("查询护理级别详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<CareLevelDTO> getCareLevelById(@PathVariable Long id) {
        CareLevelDTO careLevel = careLevelService.getCareLevelById(id);
        return Result.success(careLevel);
    }

    @ApiOperation("添加护理级别")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addCareLevel(@Valid @RequestBody CareLevelDTO careLevelDTO) {
        Long careLevelId = careLevelService.addCareLevel(careLevelDTO);
        return Result.success("护理级别添加成功", careLevelId);
    }

    @ApiOperation("修改护理级别")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateCareLevel(@Valid @RequestBody CareLevelDTO careLevelDTO) {
        careLevelService.updateCareLevel(careLevelDTO);
        return Result.success("护理级别修改成功");
    }

    @ApiOperation("删除护理级别")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteCareLevel(@PathVariable Long id) {
        careLevelService.deleteCareLevel(id);
        return Result.success("护理级别删除成功");
    }

    @ApiOperation("查询护理级别下的护理项目")
    @GetMapping("/{id}/items")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CareItemDTO>> getItemsByLevelId(@PathVariable Long id) {
        List<CareItemDTO> items = careLevelService.getItemsByLevelId(id);
        return Result.success(items);
    }

    @ApiOperation("查询可添加到护理级别的护理项目")
    @GetMapping("/{id}/available-items")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CareItemDTO>> getAvailableItemsForLevel(@PathVariable Long id) {
        List<CareItemDTO> items = careLevelService.getAvailableItemsForLevel(id);
        return Result.success(items);
    }

    @ApiOperation("为护理级别配置护理项目")
    @PostMapping("/{id}/configure-items")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> configureItems(@PathVariable Long id, @RequestBody List<Long> careItemIds) {
        careLevelService.configureItems(id, careItemIds);
        return Result.success("护理项目配置成功");
    }

    @ApiOperation("从护理级别中移除护理项目")
    @PostMapping("/{id}/remove-items")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> removeItems(@PathVariable Long id, @RequestBody List<Long> careItemIds) {
        careLevelService.removeItems(id, careItemIds);
        return Result.success("护理项目移除成功");
    }

    @ApiOperation("查询启用状态的护理级别列表")
    @GetMapping("/enabled")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<CareLevelDTO>> getEnabledCareLevels() {
        List<CareLevelDTO> levels = careLevelService.getEnabledCareLevels();
        return Result.success(levels);
    }
}
