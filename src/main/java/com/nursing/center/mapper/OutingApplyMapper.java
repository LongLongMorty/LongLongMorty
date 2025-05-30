package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:57
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.dto.OutingApplyDTO;
import com.nursing.center.dto.OutingApplyQueryDTO;
import com.nursing.center.entity.OutingApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OutingApplyMapper extends BaseMapper<OutingApply> {

    /**
     * 分页查询外出申请
     */
    IPage<OutingApplyDTO> selectOutingApplyPage(Page<OutingApplyDTO> page, @Param("query") OutingApplyQueryDTO query);

    /**
     * 根据ID查询外出申请详情
     */
    OutingApplyDTO selectOutingApplyById(@Param("id") Long id);
}
