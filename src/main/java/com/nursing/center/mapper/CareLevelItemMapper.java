package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:34
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.entity.CareLevelItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareLevelItemMapper extends BaseMapper<CareLevelItem> {

    /**
     * 查询护理级别下的护理项目
     */
    List<CareItemDTO> selectItemsByLevelId(@Param("careLevelId") Long careLevelId);

    /**
     * 查询不在指定护理级别下的启用护理项目
     */
    List<CareItemDTO> selectAvailableItemsNotInLevel(@Param("careLevelId") Long careLevelId);

    /**
     * 批量删除护理级别下的护理项目
     */
    void deleteByCareItemIds(@Param("careLevelId") Long careLevelId, @Param("careItemIds") List<Long> careItemIds);
}
