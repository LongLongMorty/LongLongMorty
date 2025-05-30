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
import com.nursing.center.entity.HealthManagerCustomer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HealthManagerCustomerMapper extends BaseMapper<HealthManagerCustomer> {
}
