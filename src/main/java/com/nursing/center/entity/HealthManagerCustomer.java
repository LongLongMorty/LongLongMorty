package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  14:06
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("health_manager_customer")
public class HealthManagerCustomer extends BaseEntity {
    private Long healthManagerId;
    private Long customerId;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private Integer status; // 0-停止服务 1-正在服务
}
