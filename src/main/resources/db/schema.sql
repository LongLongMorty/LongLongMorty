-- 东软颐养中心管理系统数据库脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS nursing_center DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE nursing_center;

-- 1. 系统用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `username` varchar(50) NOT NULL COMMENT '用户名',
                            `password` varchar(255) NOT NULL COMMENT '密码',
                            `real_name` varchar(50) NOT NULL COMMENT '真实姓名',
                            `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                            `role` varchar(20) NOT NULL COMMENT '角色：ADMIN-管理员, HEALTH_MANAGER-健康管家',
                            `status` tinyint DEFAULT '1' COMMENT '状态：0-禁用，1-启用',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 2. 楼栋表
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `building_no` varchar(10) NOT NULL COMMENT '楼栋编号',
                            `building_name` varchar(50) NOT NULL COMMENT '楼栋名称',
                            `floor_count` int DEFAULT '0' COMMENT '楼层数',
                            `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_building_no` (`building_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楼栋表';

-- 3. 房间表
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `building_id` bigint NOT NULL COMMENT '楼栋ID',
                        `room_no` varchar(10) NOT NULL COMMENT '房间号',
                        `room_name` varchar(50) DEFAULT NULL COMMENT '房间名称',
                        `floor_no` int NOT NULL COMMENT '楼层号',
                        `bed_count` int DEFAULT '0' COMMENT '床位数量',
                        `room_type` varchar(20) DEFAULT 'STANDARD' COMMENT '房间类型：STANDARD-标准间，VIP-贵宾间',
                        `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                        PRIMARY KEY (`id`),
                        KEY `idx_building_id` (`building_id`),
                        UNIQUE KEY `uk_building_room` (`building_id`, `room_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房间表';

-- 4. 床位表
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed` (
                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                       `room_id` bigint NOT NULL COMMENT '房间ID',
                       `bed_no` varchar(10) NOT NULL COMMENT '床位号',
                       `bed_type` varchar(20) DEFAULT 'STANDARD' COMMENT '床位类型：STANDARD-标准床，CARE-护理床',
                       `bed_status` varchar(20) DEFAULT 'AVAILABLE' COMMENT '床位状态：AVAILABLE-空闲，OCCUPIED-有人，OUT-外出',
                       `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                       PRIMARY KEY (`id`),
                       KEY `idx_room_id` (`room_id`),
                       UNIQUE KEY `uk_room_bed` (`room_id`, `bed_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='床位表';

-- 5. 客户表
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                            `customer_name` varchar(50) NOT NULL COMMENT '客户姓名',
                            `age` int DEFAULT NULL COMMENT '年龄',
                            `gender` varchar(10) NOT NULL COMMENT '性别：MALE-男，FEMALE-女',
                            `id_card` varchar(18) NOT NULL COMMENT '身份证号',
                            `birth_date` date DEFAULT NULL COMMENT '出生日期',
                            `blood_type` varchar(10) DEFAULT NULL COMMENT '血型：A、B、AB、O',
                            `guardian_name` varchar(50) DEFAULT NULL COMMENT '家属姓名',
                            `guardian_phone` varchar(20) DEFAULT NULL COMMENT '家属联系电话',
                            `building_id` bigint DEFAULT NULL COMMENT '楼栋ID',
                            `room_id` bigint DEFAULT NULL COMMENT '房间ID',
                            `bed_id` bigint DEFAULT NULL COMMENT '床位ID',
                            `check_in_date` date DEFAULT NULL COMMENT '入住时间',
                            `contract_expire_date` date DEFAULT NULL COMMENT '合同到期时间',
                            `care_level_id` bigint DEFAULT NULL COMMENT '护理级别ID',
                            `health_manager_id` bigint DEFAULT NULL COMMENT '健康管家ID',
                            `customer_type` varchar(20) DEFAULT 'SELF_CARE' COMMENT '客户类型：SELF_CARE-自理老人，CARE-护理老人',
                            `status` tinyint DEFAULT '1' COMMENT '状态：0-已退住，1-在住',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_id_card` (`id_card`),
                            KEY `idx_health_manager_id` (`health_manager_id`),
                            KEY `idx_care_level_id` (`care_level_id`),
                            KEY `idx_bed_id` (`bed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表';

-- 6. 床位使用详情表
DROP TABLE IF EXISTS `bed_usage`;
CREATE TABLE `bed_usage` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `customer_id` bigint NOT NULL COMMENT '客户ID',
                             `bed_id` bigint NOT NULL COMMENT '床位ID',
                             `room_id` bigint NOT NULL COMMENT '房间ID',
                             `building_id` bigint NOT NULL COMMENT '楼栋ID',
                             `start_date` date NOT NULL COMMENT '入住开始时间',
                             `end_date` date DEFAULT NULL COMMENT '入住结束时间',
                             `usage_status` varchar(20) DEFAULT 'USING' COMMENT '使用状态：USING-正在使用，HISTORY-使用历史',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                             PRIMARY KEY (`id`),
                             KEY `idx_customer_id` (`customer_id`),
                             KEY `idx_bed_id` (`bed_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='床位使用详情表';

-- 7. 护理级别表
DROP TABLE IF EXISTS `care_level`;
CREATE TABLE `care_level` (
                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                              `level_name` varchar(50) NOT NULL COMMENT '护理级别名称',
                              `level_code` varchar(20) NOT NULL COMMENT '护理级别编码',
                              `description` text COMMENT '描述',
                              `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `uk_level_code` (`level_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理级别表';

-- 8. 护理项目表
DROP TABLE IF EXISTS `care_item`;
CREATE TABLE `care_item` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `item_code` varchar(20) NOT NULL COMMENT '项目编号',
                             `item_name` varchar(100) NOT NULL COMMENT '项目名称',
                             `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
                             `execute_cycle` int DEFAULT '1' COMMENT '执行周期（天）',
                             `execute_times` int DEFAULT '1' COMMENT '执行次数',
                             `description` text COMMENT '描述',
                             `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_item_code` (`item_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理项目表';

-- 9. 护理级别项目关联表
DROP TABLE IF EXISTS `care_level_item`;
CREATE TABLE `care_level_item` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `care_level_id` bigint NOT NULL COMMENT '护理级别ID',
                                   `care_item_id` bigint NOT NULL COMMENT '护理项目ID',
                                   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_level_item` (`care_level_id`, `care_item_id`),
                                   KEY `idx_care_level_id` (`care_level_id`),
                                   KEY `idx_care_item_id` (`care_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理级别项目关联表';

-- 10. 客户护理服务表
DROP TABLE IF EXISTS `customer_care`;
CREATE TABLE `customer_care` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `customer_id` bigint NOT NULL COMMENT '客户ID',
                                 `care_item_id` bigint NOT NULL COMMENT '护理项目ID',
                                 `purchase_date` date NOT NULL COMMENT '购买服务日期',
                                 `purchase_quantity` int DEFAULT '1' COMMENT '购买数量',
                                 `used_quantity` int DEFAULT '0' COMMENT '已使用数量',
                                 `remaining_quantity` int DEFAULT '0' COMMENT '剩余数量',
                                 `expire_date` date NOT NULL COMMENT '服务到期日期',
                                 `service_status` varchar(20) DEFAULT 'NORMAL' COMMENT '服务状态：NORMAL-正常，EXPIRED-到期，ARREARS-欠费，USED_UP-用完',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_customer_id` (`customer_id`),
                                 KEY `idx_care_item_id` (`care_item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户护理服务表';

-- 11. 护理记录表
DROP TABLE IF EXISTS `care_record`;
CREATE TABLE `care_record` (
                               `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                               `customer_id` bigint NOT NULL COMMENT '客户ID',
                               `care_item_id` bigint NOT NULL COMMENT '护理项目ID',
                               `health_manager_id` bigint NOT NULL COMMENT '健康管家ID',
                               `care_time` datetime NOT NULL COMMENT '护理时间',
                               `care_quantity` int DEFAULT '1' COMMENT '护理数量',
                               `care_content` text COMMENT '护理内容',
                               `remark` text COMMENT '备注',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                               PRIMARY KEY (`id`),
                               KEY `idx_customer_id` (`customer_id`),
                               KEY `idx_care_item_id` (`care_item_id`),
                               KEY `idx_health_manager_id` (`health_manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='护理记录表';

-- 12. 退住申请表
DROP TABLE IF EXISTS `checkout_apply`;
CREATE TABLE `checkout_apply` (
                                  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                  `customer_id` bigint NOT NULL COMMENT '客户ID',
                                  `applicant_id` bigint NOT NULL COMMENT '申请人ID（健康管家）',
                                  `checkout_type` varchar(20) NOT NULL COMMENT '退住类型：NORMAL-正常退住，DEATH-死亡退住，RESERVE-保留床位',
                                  `checkout_reason` text COMMENT '退住原因',
                                  `checkout_date` date NOT NULL COMMENT '退住时间',
                                  `apply_status` varchar(20) DEFAULT 'SUBMITTED' COMMENT '申请状态：SUBMITTED-已提交，APPROVED-通过，REJECTED-不通过',
                                  `approver_id` bigint DEFAULT NULL COMMENT '审批人ID',
                                  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
                                  `approve_remark` text COMMENT '审批备注',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_customer_id` (`customer_id`),
                                  KEY `idx_applicant_id` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退住申请表';

-- 13. 外出申请表
DROP TABLE IF EXISTS `outing_apply`;
CREATE TABLE `outing_apply` (
                                `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                `customer_id` bigint NOT NULL COMMENT '客户ID',
                                `applicant_id` bigint NOT NULL COMMENT '申请人ID（健康管家）',
                                `outing_reason` text NOT NULL COMMENT '外出事由',
                                `outing_date` datetime NOT NULL COMMENT '外出时间',
                                `expected_return_date` datetime NOT NULL COMMENT '预计回院时间',
                                `actual_return_date` datetime DEFAULT NULL COMMENT '实际回院时间',
                                `apply_status` varchar(20) DEFAULT 'SUBMITTED' COMMENT '申请状态：SUBMITTED-已提交，APPROVED-通过，REJECTED-不通过',
                                `approver_id` bigint DEFAULT NULL COMMENT '审批人ID',
                                `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
                                `approve_remark` text COMMENT '审批备注',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                PRIMARY KEY (`id`),
                                KEY `idx_customer_id` (`customer_id`),
                                KEY `idx_applicant_id` (`applicant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外出申请表';

-- 14. 膳食日历表
DROP TABLE IF EXISTS `meal_calendar`;
CREATE TABLE `meal_calendar` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `meal_date` date NOT NULL COMMENT '膳食日期',
                                 `meal_type` varchar(20) NOT NULL COMMENT '餐类型：BREAKFAST-早餐，LUNCH-午餐，DINNER-晚餐',
                                 `meal_name` varchar(100) NOT NULL COMMENT '膳食名称',
                                 `meal_category` varchar(50) DEFAULT NULL COMMENT '膳食品类',
                                 `description` text COMMENT '描述',
                                 `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `uk_meal_date_type` (`meal_date`, `meal_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='膳食日历表';

-- 15. 客户膳食配置表
DROP TABLE IF EXISTS `customer_meal_config`;
CREATE TABLE `customer_meal_config` (
                                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                        `customer_id` bigint NOT NULL COMMENT '客户ID',
                                        `meal_type` varchar(20) NOT NULL COMMENT '餐类型：BREAKFAST-早餐，LUNCH-午餐，DINNER-晚餐',
                                        `special_requirements` text COMMENT '特殊要求',
                                        `allergies` text COMMENT '过敏信息',
                                        `dietary_restrictions` text COMMENT '饮食限制',
                                        `status` tinyint DEFAULT '1' COMMENT '状态：0-停用，1-启用',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                        PRIMARY KEY (`id`),
                                        KEY `idx_customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户膳食配置表';

-- 16. 操作日志表
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
                                 `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                 `user_id` bigint NOT NULL COMMENT '操作用户ID',
                                 `operation_type` varchar(50) NOT NULL COMMENT '操作类型',
                                 `operation_desc` text COMMENT '操作描述',
                                 `request_method` varchar(10) DEFAULT NULL COMMENT '请求方法',
                                 `request_url` varchar(500) DEFAULT NULL COMMENT '请求URL',
                                 `request_params` text COMMENT '请求参数',
                                 `response_result` text COMMENT '响应结果',
                                 `ip_address` varchar(50) DEFAULT NULL COMMENT 'IP地址',
                                 `user_agent` varchar(500) DEFAULT NULL COMMENT '用户代理',
                                 `execute_time` bigint DEFAULT NULL COMMENT '执行时间（毫秒）',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_user_id` (`user_id`),
                                 KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';