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
import com.nursing.center.dto.CustomerCareDTO;
import com.nursing.center.entity.CustomerCare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerCareMapper extends BaseMapper<CustomerCare> {

    /**
     * 查询客户的护理服务列表
     */
    List<CustomerCareDTO> selectByCustomerId(@Param("customerId") Long customerId);

    /**
     * 查询客户未拥有的护理项目
     */
    List<CustomerCareDTO> selectAvailableItemsForCustomer(@Param("customerId") Long customerId, @Param("itemName") String itemName);

    /**
     * 更新客户护理服务使用数量
     */
    void updateUsedQuantity(@Param("id") Long id, @Param("usedQuantity") Integer usedQuantity, @Param("remainingQuantity") Integer remainingQuantity);
}
