package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:29
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.DailyMealDTO;
import com.nursing.center.dto.MealCalendarDTO;
import com.nursing.center.dto.MealCalendarQueryDTO;
import com.nursing.center.dto.WeeklyMealPlanDTO;

import java.time.LocalDate;
import java.util.List;

public interface MealCalendarService {

    /**
     * 分页查询膳食日历
     */
    IPage<MealCalendarDTO> getMealCalendarPage(MealCalendarQueryDTO query);

    /**
     * 根据ID查询膳食日历
     */
    MealCalendarDTO getMealCalendarById(Long id);

    /**
     * 添加膳食日历
     */
    Long addMealCalendar(MealCalendarDTO mealCalendarDTO);

    /**
     * 修改膳食日历
     */
    void updateMealCalendar(MealCalendarDTO mealCalendarDTO);

    /**
     * 删除膳食日历
     */
    void deleteMealCalendar(Long id);

    /**
     * 批量创建周期膳食安排
     */
    void createWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO);

    /**
     * 查询指定日期范围的膳食安排
     */
    List<DailyMealDTO> getMealsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * 查询指定日期的膳食安排
     */
    List<MealCalendarDTO> getMealsByDate(LocalDate mealDate);

    /**
     * 复制膳食安排到指定日期
     */
    void copyMealsToDate(LocalDate sourceDate, LocalDate targetDate);
}
