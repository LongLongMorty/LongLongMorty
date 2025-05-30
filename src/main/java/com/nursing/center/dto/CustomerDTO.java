package com.nursing.center.dto;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.dto
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:07
 * @Description: TODO
 * @Version: 1.0
 */
import com.nursing.center.common.enums.CustomerType;
import com.nursing.center.common.enums.Gender;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "客户姓名不能为空")
    private String customerName;

    private Integer age;

    @NotNull(message = "性别不能为空")
    private Gender gender;

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "^\\d{17}[\\dXx]$", message = "身份证号格式不正确")
    private String idCard;

    private LocalDate birthDate;
    private String bloodType;
    private String guardianName;
    private String guardianPhone;

    @NotNull(message = "楼栋不能为空")
    private Long buildingId;

    @NotNull(message = "房间不能为空")
    private Long roomId;

    @NotNull(message = "床位不能为空")
    private Long bedId;

    @NotNull(message = "入住时间不能为空")
    private LocalDate checkInDate;

    @NotNull(message = "合同到期时间不能为空")
    private LocalDate contractExpireDate;

    private Long careLevelId;
    private Long healthManagerId;
    private CustomerType customerType;
    private Integer status;

    // 关联信息（用于展示）
    private String buildingName;
    private String roomNo;
    private String bedNo;
    private String careLevelName;
    private String healthManagerName;
}
