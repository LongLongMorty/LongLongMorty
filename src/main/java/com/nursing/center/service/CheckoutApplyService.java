package com.nursing.center.service;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:58
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nursing.center.dto.CheckoutApplyDTO;
import com.nursing.center.dto.CheckoutApplyQueryDTO;
import com.nursing.center.dto.CheckoutApproveDTO;

public interface CheckoutApplyService {

    /**
     * 分页查询退住申请
     */
    IPage<CheckoutApplyDTO> getCheckoutApplyPage(CheckoutApplyQueryDTO query);

    /**
     * 根据ID查询退住申请详情
     */
    CheckoutApplyDTO getCheckoutApplyById(Long id);

    /**
     * 提交退住申请
     */
    Long submitCheckoutApply(CheckoutApplyDTO checkoutApplyDTO);

    /**
     * 审批退住申请
     */
    void approveCheckoutApply(CheckoutApproveDTO approveDTO);

    /**
     * 删除退住申请
     */
    void deleteCheckoutApply(Long id);
}
