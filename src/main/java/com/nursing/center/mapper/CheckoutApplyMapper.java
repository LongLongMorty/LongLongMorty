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
import com.nursing.center.dto.CheckoutApplyDTO;
import com.nursing.center.dto.CheckoutApplyQueryDTO;
import com.nursing.center.entity.CheckoutApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CheckoutApplyMapper extends BaseMapper<CheckoutApply> {

    /**
     * 分页查询退住申请
     */
    IPage<CheckoutApplyDTO> selectCheckoutApplyPage(Page<CheckoutApplyDTO> page, @Param("query") CheckoutApplyQueryDTO query);

    /**
     * 根据ID查询退住申请详情
     */
    CheckoutApplyDTO selectCheckoutApplyById(@Param("id") Long id);
}
