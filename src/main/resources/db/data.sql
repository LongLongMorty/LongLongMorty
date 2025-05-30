-- 初始化数据脚本

-- 1. 初始化系统用户（密码为账号相同，实际存储时会加密）
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `role`, `status`) VALUES
                                                                                            ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '系统管理员', '13800000001', 'ADMIN', 1),
                                                                                            ('admin1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '管理员1', '13800000002', 'ADMIN', 1),
                                                                                            ('admin2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '管理员2', '13800000003', 'ADMIN', 1);

-- 2. 初始化楼栋信息（固定606）
INSERT INTO `building` (`building_no`, `building_name`, `floor_count`, `status`) VALUES
    ('606', '606号楼', 6, 1);

-- 3. 初始化房间信息（示例：每层10个房间）
INSERT INTO `room` (`building_id`, `room_no`, `room_name`, `floor_no`, `bed_count`, `room_type`, `status`) VALUES
                                                                                                               (1, '101', '101室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '102', '102室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '103', '103室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '104', '104室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '105', '105室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '106', '106室', 1, 4, 'VIP', 1),
                                                                                                               (1, '107', '107室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '108', '108室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '109', '109室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '110', '110室', 1, 2, 'STANDARD', 1),
                                                                                                               (1, '201', '201室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '202', '202室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '203', '203室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '204', '204室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '205', '205室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '206', '206室', 2, 4, 'VIP', 1),
                                                                                                               (1, '207', '207室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '208', '208室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '209', '209室', 2, 2, 'STANDARD', 1),
                                                                                                               (1, '210', '210室', 2, 2, 'STANDARD', 1);

-- 4. 初始化床位信息
-- 101室的床位
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (1, 'A', 'STANDARD', 'AVAILABLE', 1),
                                                                                (1, 'B', 'STANDARD', 'AVAILABLE', 1);

-- 102室的床位
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (2, 'A', 'STANDARD', 'AVAILABLE', 1),
                                                                                (2, 'B', 'STANDARD', 'AVAILABLE', 1);

-- 103室的床位
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (3, 'A', 'STANDARD', 'AVAILABLE', 1),
                                                                                (3, 'B', 'STANDARD', 'AVAILABLE', 1);

-- 104室的床位
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (4, 'A', 'STANDARD', 'AVAILABLE', 1),
                                                                                (4, 'B', 'STANDARD', 'AVAILABLE', 1);

-- 105室的床位
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (5, 'A', 'STANDARD', 'AVAILABLE', 1),
                                                                                (5, 'B', 'STANDARD', 'AVAILABLE', 1);

-- 106室的床位（VIP房间）
INSERT INTO `bed` (`room_id`, `bed_no`, `bed_type`, `bed_status`, `status`) VALUES
                                                                                (6, 'A', 'CARE', 'AVAILABLE', 1),
                                                                                (6, 'B', 'CARE', 'AVAILABLE', 1),
                                                                                (6, 'C', 'CARE', 'AVAILABLE', 1),
                                                                                (6, 'D', 'CARE', 'AVAILABLE', 1);

-- 继续为其他房间创建床位...（此处省略，实际项目中需要完整创建）

-- 5. 初始化护理级别
INSERT INTO `care_level` (`level_name`, `level_code`, `description`, `status`) VALUES
                                                                                   ('一级护理', 'LEVEL_1', '轻度护理，适用于生活基本自理的老人', 1),
                                                                                   ('二级护理', 'LEVEL_2', '中度护理，适用于生活部分自理的老人', 1),
                                                                                   ('三级护理', 'LEVEL_3', '重度护理，适用于生活完全不能自理的老人', 1),
                                                                                   ('特级护理', 'LEVEL_SPECIAL', '特殊护理，适用于需要专业医疗护理的老人', 1);

-- 6. 初始化护理项目
INSERT INTO `care_item` (`item_code`, `item_name`, `price`, `execute_cycle`, `execute_times`, `description`, `status`) VALUES
                                                                                                                           ('CARE_001', '日常洗漱协助', 50.00, 1, 2, '协助老人进行日常洗漱活动', 1),
                                                                                                                           ('CARE_002', '用餐协助', 30.00, 1, 3, '协助老人用餐，确保营养摄入', 1),
                                                                                                                           ('CARE_003', '翻身护理', 80.00, 1, 4, '定时为卧床老人翻身，预防褥疮', 1),
                                                                                                                           ('CARE_004', '药物管理', 100.00, 1, 2, '协助老人按时服药', 1),
                                                                                                                           ('CARE_005', '康复训练', 150.00, 1, 1, '进行康复训练指导', 1),
                                                                                                                           ('CARE_006', '心理疏导', 200.00, 7, 1, '提供心理健康疏导服务', 1),
                                                                                                                           ('CARE_007', '健康监测', 120.00, 1, 2, '监测血压、血糖等健康指标', 1),
                                                                                                                           ('CARE_008', '生活护理', 80.00, 1, 1, '协助日常生活护理', 1);

-- 7. 初始化护理级别与项目关联
-- 一级护理项目
INSERT INTO `care_level_item` (`care_level_id`, `care_item_id`) VALUES
                                                                    (1, 1), (1, 2), (1, 4), (1, 7);

-- 二级护理项目
INSERT INTO `care_level_item` (`care_level_id`, `care_item_id`) VALUES
                                                                    (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 7), (2, 8);

-- 三级护理项目
INSERT INTO `care_level_item` (`care_level_id`, `care_item_id`) VALUES
                                                                    (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 8);

-- 特级护理项目
INSERT INTO `care_level_item` (`care_level_id`, `care_item_id`) VALUES
                                                                    (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6), (4, 7), (4, 8);

-- 8. 初始化健康管家用户
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `phone`, `role`, `status`) VALUES
                                                                                            ('nurse001', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '张护士', '13800001001', 'HEALTH_MANAGER', 1),
                                                                                            ('nurse002', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '李护士', '13800001002', 'HEALTH_MANAGER', 1),
                                                                                            ('nurse003', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8imdQMkhskqfLaOCzf3xCl5HgFzn2', '王护士', '13800001003', 'HEALTH_MANAGER', 1);

-- 9. 初始化膳食日历示例数据
INSERT INTO `meal_calendar` (`meal_date`, `meal_type`, `meal_name`, `meal_category`, `description`, `status`) VALUES
                                                                                                                  ('2024-01-01', 'BREAKFAST', '小米粥配咸菜', '清淡', '营养丰富的早餐', 1),
                                                                                                                  ('2024-01-01', 'LUNCH', '红烧肉配米饭', '荤菜', '营养均衡的午餐', 1),
                                                                                                                  ('2024-01-01', 'DINNER', '蔬菜汤面', '素食', '清淡易消化的晚餐', 1),
                                                                                                                  ('2024-01-02', 'BREAKFAST', '燕麦粥配水果', '清淡', '高纤维早餐', 1),
                                                                                                                  ('2024-01-02', 'LUNCH', '清蒸鱼配蔬菜', '清淡', '高蛋白午餐', 1),
                                                                                                                  ('2024-01-02', 'DINNER', '蔬菜粥', '素食', '养胃晚餐', 1);