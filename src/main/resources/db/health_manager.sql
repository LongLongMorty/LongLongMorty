-- 健康管家客户关联表
DROP TABLE IF EXISTS `health_manager_customer`;
CREATE TABLE `health_manager_customer` (
                                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                           `health_manager_id` bigint NOT NULL COMMENT '健康管家ID',
                                           `customer_id` bigint NOT NULL COMMENT '客户ID',
                                           `service_start_date` date NOT NULL COMMENT '服务开始日期',
                                           `service_end_date` date DEFAULT NULL COMMENT '服务结束日期',
                                           `status` tinyint DEFAULT '1' COMMENT '服务状态：0-停止服务，1-正在服务',
                                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                           `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除：0-未删除，1-已删除',
                                           PRIMARY KEY (`id`),
                                           KEY `idx_health_manager_id` (`health_manager_id`),
                                           KEY `idx_customer_id` (`customer_id`),
                                           KEY `idx_service_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='健康管家客户关联表';

-- 创建索引
CREATE INDEX idx_health_manager_customer_service ON health_manager_customer(health_manager_id, customer_id, status);
CREATE INDEX idx_health_manager_customer_date ON health_manager_customer(service_start_date, service_end_date);