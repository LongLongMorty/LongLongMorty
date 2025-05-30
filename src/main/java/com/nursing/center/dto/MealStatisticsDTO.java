package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:27
 * @Description: TODO
 * @Version: 1.0
 */
import lombok.Data;

@Data
public class MealStatisticsDTO {
    private Integer totalCustomers; // 总客户数
    private Integer configuredCustomers; // 已配置膳食的客户数
    private Integer unconfiguredCustomers; // 未配置膳食的客户数

    // 各餐类型配置统计
    private Integer breakfastConfigs;
    private Integer lunchConfigs;
    private Integer dinnerConfigs;
    private Integer snackConfigs;

    // 特殊需求统计
    private Integer allergicCustomers; // 有过敏信息的客户数
    private Integer restrictedCustomers; // 有饮食限制的客户数
}
