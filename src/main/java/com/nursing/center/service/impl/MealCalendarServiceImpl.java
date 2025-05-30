package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:30
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.DailyMealDTO;
import com.nursing.center.dto.MealCalendarDTO;
import com.nursing.center.dto.MealCalendarQueryDTO;
import com.nursing.center.dto.WeeklyMealPlanDTO;
import com.nursing.center.entity.MealCalendar;
import com.nursing.center.mapper.MealCalendarMapper;
import com.nursing.center.service.MealCalendarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealCalendarServiceImpl implements MealCalendarService {

    private final MealCalendarMapper mealCalendarMapper;

    @Override
    public IPage<MealCalendarDTO> getMealCalendarPage(MealCalendarQueryDTO query) {
        Page<MealCalendarDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return mealCalendarMapper.selectMealCalendarPage(page, query);
    }

    @Override
    public MealCalendarDTO getMealCalendarById(Long id) {
        MealCalendar mealCalendar = mealCalendarMapper.selectById(id);
        if (mealCalendar == null) {
            return null;
        }

        MealCalendarDTO dto = new MealCalendarDTO();
        BeanUtils.copyProperties(mealCalendar, dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addMealCalendar(MealCalendarDTO mealCalendarDTO) {
        // 检查是否存在冲突
        checkMealConflict(mealCalendarDTO.getMealDate(), mealCalendarDTO.getMealType().name(), null);

        MealCalendar mealCalendar = new MealCalendar();
        BeanUtils.copyProperties(mealCalendarDTO, mealCalendar);
        mealCalendar.setStatus(1); // 默认启用

        mealCalendarMapper.insert(mealCalendar);

        log.info("新增膳食日历成功，ID: {}, 日期: {}, 餐类型: {}, 名称: {}",
                mealCalendar.getId(), mealCalendar.getMealDate(), mealCalendar.getMealType(), mealCalendar.getMealName());

        return mealCalendar.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMealCalendar(MealCalendarDTO mealCalendarDTO) {
        // 验证膳食日历是否存在
        MealCalendar existMeal = mealCalendarMapper.selectById(mealCalendarDTO.getId());
        if (existMeal == null) {
            throw new BusinessException("膳食日历不存在");
        }

        // 检查是否存在冲突（排除自己）
        checkMealConflict(mealCalendarDTO.getMealDate(), mealCalendarDTO.getMealType().name(), mealCalendarDTO.getId());

        MealCalendar mealCalendar = new MealCalendar();
        BeanUtils.copyProperties(mealCalendarDTO, mealCalendar);

        mealCalendarMapper.updateById(mealCalendar);

        log.info("更新膳食日历成功，ID: {}, 日期: {}, 餐类型: {}, 名称: {}",
                mealCalendar.getId(), mealCalendar.getMealDate(), mealCalendar.getMealType(), mealCalendar.getMealName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMealCalendar(Long id) {
        MealCalendar mealCalendar = mealCalendarMapper.selectById(id);
        if (mealCalendar == null) {
            throw new BusinessException("膳食日历不存在");
        }

        mealCalendarMapper.deleteById(id);

        log.info("删除膳食日历成功，ID: {}, 日期: {}, 餐类型: {}, 名称: {}",
                id, mealCalendar.getMealDate(), mealCalendar.getMealType(), mealCalendar.getMealName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createWeeklyMealPlan(WeeklyMealPlanDTO weeklyMealPlanDTO) {
        LocalDate startDate = weeklyMealPlanDTO.getStartDate();

        for (MealCalendarDTO mealPlan : weeklyMealPlanDTO.getMealPlans()) {
            // 检查是否存在冲突
            Integer conflictCount = mealCalendarMapper.checkMealConflict(
                    mealPlan.getMealDate(), mealPlan.getMealType().name(), null);

            if (conflictCount > 0) {
                log.warn("膳食安排存在冲突，跳过：日期 {}, 餐类型 {}",
                        mealPlan.getMealDate(), mealPlan.getMealType());
                continue;
            }

            MealCalendar mealCalendar = new MealCalendar();
            BeanUtils.copyProperties(mealPlan, mealCalendar);
            mealCalendar.setStatus(1); // 默认启用

            mealCalendarMapper.insert(mealCalendar);
        }

        log.info("创建周期膳食安排成功，开始日期: {}, 膳食数量: {}",
                startDate, weeklyMealPlanDTO.getMealPlans().size());
    }

    @Override
    public List<DailyMealDTO> getMealsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<MealCalendarDTO> meals = mealCalendarMapper.selectMealsByDateRange(startDate, endDate);

        // 按日期分组
        Map<LocalDate, List<MealCalendarDTO>> mealsByDate = meals.stream()
                .collect(Collectors.groupingBy(MealCalendarDTO::getMealDate));

        List<DailyMealDTO> dailyMeals = new ArrayList<>();
        for (Map.Entry<LocalDate, List<MealCalendarDTO>> entry : mealsByDate.entrySet()) {
            DailyMealDTO dailyMeal = new DailyMealDTO();
            dailyMeal.setMealDate(entry.getKey());
            dailyMeal.setMeals(entry.getValue());
            dailyMeals.add(dailyMeal);
        }

        // 按日期排序
        dailyMeals.sort((a, b) -> a.getMealDate().compareTo(b.getMealDate()));

        return dailyMeals;
    }

    @Override
    public List<MealCalendarDTO> getMealsByDate(LocalDate mealDate) {
        return mealCalendarMapper.selectMealsByDate(mealDate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyMealsToDate(LocalDate sourceDate, LocalDate targetDate) {
        // 查询源日期的膳食安排
        List<MealCalendarDTO> sourceMeals = mealCalendarMapper.selectMealsByDate(sourceDate);

        if (sourceMeals.isEmpty()) {
            throw new BusinessException("源日期没有膳食安排");
        }

        for (MealCalendarDTO sourceMeal : sourceMeals) {
            // 检查目标日期是否已有相同餐类型的安排
            Integer conflictCount = mealCalendarMapper.checkMealConflict(
                    targetDate, sourceMeal.getMealType().name(), null);

            if (conflictCount > 0) {
                log.warn("目标日期已存在膳食安排，跳过：日期 {}, 餐类型 {}",
                        targetDate, sourceMeal.getMealType());
                continue;
            }

            // 复制膳食安排
            MealCalendar newMeal = new MealCalendar();
            BeanUtils.copyProperties(sourceMeal, newMeal);
            newMeal.setId(null); // 清空ID，让数据库自动生成
            newMeal.setMealDate(targetDate);

            mealCalendarMapper.insert(newMeal);
        }

        log.info("复制膳食安排成功，从 {} 复制到 {}, 复制数量: {}",
                sourceDate, targetDate, sourceMeals.size());
    }

    /**
     * 检查膳食安排冲突
     */
    private void checkMealConflict(LocalDate mealDate, String mealType, Long excludeId) {
        Integer conflictCount = mealCalendarMapper.checkMealConflict(mealDate, mealType, excludeId);
        if (conflictCount > 0) {
            throw new BusinessException("该日期该餐类型已存在膳食安排");
        }
    }
}
