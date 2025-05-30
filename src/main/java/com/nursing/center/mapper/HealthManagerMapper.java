package com.nursing.center.mapper;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.mapper
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:12
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.dto.CustomerServiceDTO;
import com.nursing.center.dto.HealthManagerDTO;
import com.nursing.center.dto.HealthManagerQueryDTO;
import com.nursing.center.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HealthManagerMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询健康管家
     */
    IPage<HealthManagerDTO> selectHealthManagerPage(Page<HealthManagerDTO> page, @Param("query") HealthManagerQueryDTO query);

    /**
     * 查询健康管家的服务客户列表
     */
    List<CustomerServiceDTO> selectServiceCustomers(@Param("healthManagerId") Long healthManagerId);

    /**
     * 查询无健康管家的客户
     */
    List<CustomerServiceDTO> selectCustomersWithoutManager(@Param("customerName") String customerName);

    /**
     * 查询健康管家服务的客户护理项目
     */
    List<com.nursing.center.dto.CustomerCareItemDTO> selectCustomerCareItems(@Param("customerId") Long customerId);
}
