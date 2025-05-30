-- 数据库索引优化脚本

-- 客户表相关索引
CREATE INDEX idx_customer_name ON customer(customer_name);
CREATE INDEX idx_customer_type ON customer(customer_type);
CREATE INDEX idx_customer_status ON customer(status);
CREATE INDEX idx_customer_checkin_date ON customer(check_in_date);

-- 床位使用详情表索引
CREATE INDEX idx_bed_usage_status ON bed_usage(usage_status);
CREATE INDEX idx_bed_usage_date ON bed_usage(start_date, end_date);

-- 护理记录表索引
CREATE INDEX idx_care_record_time ON care_record(care_time);
CREATE INDEX idx_care_record_customer_time ON care_record(customer_id, care_time);

-- 申请表索引
CREATE INDEX idx_checkout_apply_status ON checkout_apply(apply_status);
CREATE INDEX idx_checkout_apply_date ON checkout_apply(checkout_date);
CREATE INDEX idx_outing_apply_status ON outing_apply(apply_status);
CREATE INDEX idx_outing_apply_date ON outing_apply(outing_date);

-- 客户护理服务表索引
CREATE INDEX idx_customer_care_status ON customer_care(service_status);
CREATE INDEX idx_customer_care_expire ON customer_care(expire_date);

-- 膳食相关索引
CREATE INDEX idx_meal_calendar_date ON meal_calendar(meal_date);
CREATE INDEX idx_meal_calendar_type ON meal_calendar(meal_type);