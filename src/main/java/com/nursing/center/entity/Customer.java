package com.nursing.center.entity;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.entity
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:00
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.annotation.TableName;
import com.nursing.center.common.enums.CustomerType;
import com.nursing.center.common.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("customer")
public class Customer extends BaseEntity {
    private String customerName;
    private Integer age;
    private Gender gender;
    private String idCard;
    private LocalDate birthDate;
    private String bloodType;
    private String guardianName;
    private String guardianPhone;
    private Long buildingId;
    private Long roomId;
    private Long bedId;
    private LocalDate checkInDate;
    private LocalDate contractExpireDate;
    private Long careLevelId;
    private Long healthManagerId;
    private CustomerType customerType;
    private Integer status; // 0-已退住 1-在住
}
