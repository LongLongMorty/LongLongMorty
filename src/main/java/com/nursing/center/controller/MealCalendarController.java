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
import com.nursing.center.dto.DailyMealDTO;
import com.nursing.center.dto.MealCalendarDTO;
import com.nursing.center.dto.MealCalendarQueryDTO;
import com.nursing.center.dto.WeeklyMealPlanDTO;
import com.nursing.center.service.MealCalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "膳食日历管理")
@RestController
@RequestMapping("/api/admin/meal-calendar")
@RequiredArgsConstructor
@Validated
public class MealCalendarController {

    private final MealCalendarService mealCalendarService;

    @ApiOperation("分页查询膳食日历")
    @GetMapping("/page")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<IPage<MealCalendarDTO>> getMealCalendarPage(MealCalendarQueryDTO query) {
        IPage<MealCalendarDTO> page = mealCalendarService.getMealCalendarPage(query);
        return Result.success(page);
    }

    @ApiOperation("根据ID查询膳食日历")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<MealCalendarDTO> getMealCalendarById(@PathVariable Long id) {
        MealCalendarDTO mealCalendar = mealCalendarService.getMealCalendarById(id);
        return Result.success(mealCalendar);
    }

    @ApiOperation("添加膳食日历")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Long> addMealCalendar(@Valid @RequestBody MealCalendarDTO mealCalendarDTO) {
        Long id = mealCalendarService.addMealCalendar(mealCalendarDTO);
        return Result.success("膳食日历添加成功", id);
    }

    @ApiOperation("修改膳食日历")
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> updateMealCalendar(@Valid @RequestBody MealCalendarDTO mealCalendarDTO) {
        mealCalendarService.updateMealCalendar(mealCalendarDTO);
        return Result.success("膳食日历修改成功");
    }

    @ApiOperation("删除膳食日历")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> deleteMealCalendar(@PathVariable Long id) {
        mealCalendarService.deleteMealCalendar(id);
        return Result.success("膳食日历删除成功");
    }

    @ApiOperation("创建周期膳食安排")
    @PostMapping("/weekly-plan")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> createWeeklyMealPlan(@Valid @RequestBody WeeklyMealPlanDTO weeklyMealPlanDTO) {
        mealCalendarService.createWeeklyMealPlan(weeklyMealPlanDTO);
        return Result.success("周期膳食安排创建成功");
    }

    @ApiOperation("查询日期范围内的膳食安排")
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<DailyMealDTO>> getMealsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailyMealDTO> meals = mealCalendarService.getMealsByDateRange(startDate, endDate);
        return Result.success(meals);
    }

    @ApiOperation("查询指定日期的膳食安排")
    @GetMapping("/date/{mealDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<MealCalendarDTO>> getMealsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate mealDate) {
        List<MealCalendarDTO> meals = mealCalendarService.getMealsByDate(mealDate);
        return Result.success(meals);
    }

    @ApiOperation("复制膳食安排到指定日期")
    @PostMapping("/copy")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> copyMealsToDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate sourceDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        mealCalendarService.copyMealsToDate(sourceDate, targetDate);
        return Result.success("膳食安排复制成功");
    }
}
