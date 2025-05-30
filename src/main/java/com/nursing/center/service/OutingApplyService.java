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
import com.nursing.center.dto.OutingApplyDTO;
import com.nursing.center.dto.OutingApplyQueryDTO;
import com.nursing.center.dto.OutingApproveDTO;
import com.nursing.center.dto.OutingReturnDTO;

public interface OutingApplyService {

    /**
     * 分页查询外出申请
     */
    IPage<OutingApplyDTO> getOutingApplyPage(OutingApplyQueryDTO query);

    /**
     * 根据ID查询外出申请详情
     */
    OutingApplyDTO getOutingApplyById(Long id);

    /**
     * 提交外出申请
     */
    Long submitOutingApply(OutingApplyDTO outingApplyDTO);

    /**
     * 审批外出申请
     */
    void approveOutingApply(OutingApproveDTO approveDTO);

    /**
     * 登记回院
     */
    void registerReturn(OutingReturnDTO returnDTO);

    /**
     * 删除外出申请
     */
    void deleteOutingApply(Long id);
}
