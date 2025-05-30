package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:36
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.dto.CareItemQueryDTO;

import java.util.List;

public interface CareItemService {

    /**
     * 分页查询护理项目
     */
    IPage<CareItemDTO> getCareItemPage(CareItemQueryDTO query);

    /**
     * 根据ID查询护理项目
     */
    CareItemDTO getCareItemById(Long id);

    /**
     * 添加护理项目
     */
    Long addCareItem(CareItemDTO careItemDTO);

    /**
     * 修改护理项目
     */
    void updateCareItem(CareItemDTO careItemDTO);

    /**
     * 删除护理项目
     */
    void deleteCareItem(Long id);

    /**
     * 查询启用状态的护理项目列表
     */
    List<CareItemDTO> getEnabledCareItems();
}
