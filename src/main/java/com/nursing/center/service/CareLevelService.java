package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:35
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.CareLevelDTO;
import com.nursing.center.dto.CareLevelQueryDTO;
import com.nursing.center.dto.CareItemDTO;

import java.util.List;

public interface CareLevelService {

    /**
     * 分页查询护理级别
     */
    IPage<CareLevelDTO> getCareLevelPage(CareLevelQueryDTO query);

    /**
     * 查询护理级别详情（包含护理项目）
     */
    CareLevelDTO getCareLevelById(Long id);

    /**
     * 添加护理级别
     */
    Long addCareLevel(CareLevelDTO careLevelDTO);

    /**
     * 修改护理级别
     */
    void updateCareLevel(CareLevelDTO careLevelDTO);

    /**
     * 删除护理级别
     */
    void deleteCareLevel(Long id);

    /**
     * 查询护理级别下的护理项目
     */
    List<CareItemDTO> getItemsByLevelId(Long careLevelId);

    /**
     * 查询可添加到护理级别的护理项目
     */
    List<CareItemDTO> getAvailableItemsForLevel(Long careLevelId);

    /**
     * 为护理级别配置护理项目
     */
    void configureItems(Long careLevelId, List<Long> careItemIds);

    /**
     * 从护理级别中移除护理项目
     */
    void removeItems(Long careLevelId, List<Long> careItemIds);

    /**
     * 查询启用状态的护理级别列表
     */
    List<CareLevelDTO> getEnabledCareLevels();
}
