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
import com.nursing.center.dto.CustomerMealConfigDTO;
import com.nursing.center.dto.CustomerMealConfigQueryDTO;
import com.nursing.center.dto.MealStatisticsDTO;
import com.nursing.center.entity.CustomerMealConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMealConfigMapper extends BaseMapper<CustomerMealConfig> {

    /**
     * 分页查询客户膳食配置
     */
    IPage<CustomerMealConfigDTO> selectCustomerMealConfigPage(Page<CustomerMealConfigDTO> page, @Param("query") CustomerMealConfigQueryDTO query);

    /**
     * 查询客户的膳食配置
     */
    List<CustomerMealConfigDTO> selectByCustomerId(@Param("customerId") Long customerId);

    /**
     * 查询膳食统计信息
     */
    MealStatisticsDTO selectMealStatistics();

    /**
     * 查询未配置膳食的客户
     */
    List<CustomerMealConfigDTO> selectCustomersWithoutMealConfig(@Param("customerName") String customerName);
}
