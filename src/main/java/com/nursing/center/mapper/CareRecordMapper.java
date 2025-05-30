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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.dto.CareRecordDTO;
import com.nursing.center.dto.CareRecordQueryDTO;
import com.nursing.center.entity.CareRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CareRecordMapper extends BaseMapper<CareRecord> {

    /**
     * 分页查询护理记录
     */
    IPage<CareRecordDTO> selectCareRecordPage(Page<CareRecordDTO> page, @Param("query") CareRecordQueryDTO query);

    /**
     * 查询客户的护理记录
     */
    IPage<CareRecordDTO> selectByCustomerId(Page<CareRecordDTO> page, @Param("customerId") Long customerId);
}
