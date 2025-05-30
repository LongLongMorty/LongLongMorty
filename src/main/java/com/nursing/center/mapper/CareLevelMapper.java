package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:33
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.dto.CareLevelDTO;
import com.nursing.center.dto.CareLevelQueryDTO;
import com.nursing.center.entity.CareLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CareLevelMapper extends BaseMapper<CareLevel> {

    /**
     * 分页查询护理级别
     */
    IPage<CareLevelDTO> selectCareLevelPage(Page<CareLevelDTO> page, @Param("query") CareLevelQueryDTO query);

    /**
     * 查询护理级别及其关联的护理项目
     */
    CareLevelDTO selectCareLevelWithItems(@Param("id") Long id);

    /**
     * 查询启用状态的护理级别列表
     */
    List<CareLevelDTO> selectEnabledCareLevels();
}
