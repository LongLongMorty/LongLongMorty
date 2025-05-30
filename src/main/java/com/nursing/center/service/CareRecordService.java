package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:41
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.CareRecordDTO;
import com.nursing.center.dto.CareRecordQueryDTO;

public interface CareRecordService {

    /**
     * 分页查询护理记录
     */
    IPage<CareRecordDTO> getCareRecordPage(CareRecordQueryDTO query);

    /**
     * 查询客户的护理记录
     */
    IPage<CareRecordDTO> getCustomerCareRecords(Long customerId, Integer pageNum, Integer pageSize);

    /**
     * 添加护理记录
     */
    Long addCareRecord(CareRecordDTO careRecordDTO);

    /**
     * 删除护理记录
     */
    void deleteCareRecord(Long id);

    /**
     * 根据ID查询护理记录
     */
    CareRecordDTO getCareRecordById(Long id);
}
