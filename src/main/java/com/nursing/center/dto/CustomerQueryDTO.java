package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:08
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.CustomerType;
import lombok.Data;

@Data
public class CustomerQueryDTO {
    private String customerName; // 模糊查询
    private CustomerType customerType;
    private Integer status;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
