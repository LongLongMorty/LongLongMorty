package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:11
 * @Description: TODO
 * @Version: 1.0
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.enums.BedStatus;
import com.nursing.center.common.enums.CustomerType;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.CustomerDTO;
import com.nursing.center.dto.CustomerQueryDTO;
import com.nursing.center.entity.Bed;
import com.nursing.center.entity.Customer;
import com.nursing.center.mapper.BedMapper;
import com.nursing.center.mapper.CustomerMapper;
import com.nursing.center.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final BedMapper bedMapper;

    @Override
    public IPage<CustomerDTO> getCustomerPage(CustomerQueryDTO query) {
        Page<CustomerDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return customerMapper.selectCustomerPage(page, query);
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerMapper.selectCustomerById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCustomer(CustomerDTO customerDTO) {
        // 验证数据
        validateCustomerData(customerDTO);

        // 检查身份证号是否已存在
        checkIdCardExists(customerDTO.getIdCard(), null);

        // 检查床位是否可用
        checkBedAvailable(customerDTO.getBedId());

        // 转换DTO为Entity
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        // 根据出生日期计算年龄
        if (customer.getBirthDate() != null) {
            customer.setAge(calculateAge(customer.getBirthDate()));
        }

        // 设置客户类型（默认为自理老人）
        if (customer.getCustomerType() == null) {
            customer.setCustomerType(CustomerType.SELF_CARE);
        }

        // 设置状态为在住
        customer.setStatus(1);

        // 保存客户信息
        customerMapper.insert(customer);

        // 更新床位状态为有人
        updateBedStatus(customerDTO.getBedId(), BedStatus.OCCUPIED);

        log.info("新增客户成功，客户ID: {}, 姓名: {}", customer.getId(), customer.getCustomerName());
        return customer.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(CustomerDTO customerDTO) {
        // 验证客户是否存在
        Customer existCustomer = customerMapper.selectById(customerDTO.getId());
        if (existCustomer == null) {
            throw new BusinessException("客户不存在");
        }

        // 验证数据
        validateCustomerData(customerDTO);

        // 检查身份证号是否已存在（排除自己）
        checkIdCardExists(customerDTO.getIdCard(), customerDTO.getId());

        // 转换DTO为Entity
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        // 根据出生日期计算年龄
        if (customer.getBirthDate() != null) {
            customer.setAge(calculateAge(customer.getBirthDate()));
        }

        // 更新客户信息
        customerMapper.updateById(customer);

        log.info("更新客户成功，客户ID: {}, 姓名: {}", customer.getId(), customer.getCustomerName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomer(Long id) {
        // 验证客户是否存在
        Customer customer = customerMapper.selectById(id);
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }

        // 逻辑删除客户
        customerMapper.deleteById(id);

        // 释放床位
        if (customer.getBedId() != null) {
            updateBedStatus(customer.getBedId(), BedStatus.AVAILABLE);
        }

        log.info("删除客户成功，客户ID: {}, 姓名: {}", id, customer.getCustomerName());
    }

    /**
     * 验证客户数据
     */
    private void validateCustomerData(CustomerDTO customerDTO) {
        // 验证合同到期时间不能小于入住时间
        if (customerDTO.getContractExpireDate().isBefore(customerDTO.getCheckInDate())) {
            throw new BusinessException("合同到期时间不能小于入住时间");
        }
    }

    /**
     * 检查身份证号是否已存在
     */
    private void checkIdCardExists(String idCard, Long excludeId) {
        LambdaQueryWrapper<Customer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Customer::getIdCard, idCard);
        if (excludeId != null) {
            wrapper.ne(Customer::getId, excludeId);
        }

        Customer existCustomer = customerMapper.selectOne(wrapper);
        if (existCustomer != null) {
            throw new BusinessException("身份证号已存在");
        }
    }

    /**
     * 检查床位是否可用
     */
    private void checkBedAvailable(Long bedId) {
        Bed bed = bedMapper.selectById(bedId);
        if (bed == null) {
            throw new BusinessException("床位不存在");
        }
        if (!BedStatus.AVAILABLE.equals(bed.getBedStatus())) {
            throw new BusinessException("床位不可用");
        }
    }

    /**
     * 更新床位状态
     */
    private void updateBedStatus(Long bedId, BedStatus status) {
        Bed bed = new Bed();
        bed.setId(bedId);
        bed.setBedStatus(status);
        bedMapper.updateById(bed);
    }

    /**
     * 根据出生日期计算年龄
     */
    private Integer calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
