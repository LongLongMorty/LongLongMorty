package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:27
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.dto.DailyMealDTO;
import com.nursing.center.dto.MealCalendarDTO;
import com.nursing.center.dto.MealCalendarQueryDTO;
import com.nursing.center.entity.MealCalendar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MealCalendarMapper extends BaseMapper<MealCalendar> {

    /**
     * 分页查询膳食日历
     */
    IPage<MealCalendarDTO> selectMealCalendarPage(Page<MealCalendarDTO> page, @Param("query") MealCalendarQueryDTO query);

    /**
     * 查询指定日期范围的膳食安排
     */
    List<MealCalendarDTO> selectMealsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 查询指定日期的膳食安排
     */
    List<MealCalendarDTO> selectMealsByDate(@Param("mealDate") LocalDate mealDate);

    /**
     * 检查膳食安排是否存在冲突
     */
    Integer checkMealConflict(@Param("mealDate") LocalDate mealDate,
                              @Param("mealType") String mealType,
                              @Param("excludeId") Long excludeId);
}
