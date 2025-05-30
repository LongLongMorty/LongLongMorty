package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:40
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nursing.center.common.enums.CustomerType;
import com.nursing.center.common.enums.ServiceStatus;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.CustomerCareDTO;
import com.nursing.center.dto.CustomerCareLevelDTO;
import com.nursing.center.entity.Customer;
import com.nursing.center.entity.CustomerCare;
import com.nursing.center.mapper.CustomerCareMapper;
import com.nursing.center.mapper.CustomerMapper;
import com.nursing.center.service.CustomerCareService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerCareServiceImpl implements CustomerCareService {

    private final CustomerCareMapper customerCareMapper;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerCareDTO> getCustomerCareServices(Long customerId) {
        return customerCareMapper.selectByCustomerId(customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setCustomerCareLevel(CustomerCareLevelDTO customerCareLevelDTO) {
        // 验证客户是否存在
        Customer customer = customerMapper.selectById(customerCareLevelDTO.getCustomerId());
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }

        // 检查客户是否已有护理级别
        if (customer.getCareLevelId() != null) {
            throw new BusinessException("客户已有护理级别，请先移除后再设置");
        }

        // 更新客户护理级别
        Customer updateCustomer = new Customer();
        updateCustomer.setId(customerCareLevelDTO.getCustomerId());
        updateCustomer.setCareLevelId(customerCareLevelDTO.getCareLevelId());
        updateCustomer.setCustomerType(CustomerType.CARE); // 设置为护理老人
        customerMapper.updateById(updateCustomer);

        // 批量添加护理项目
        if (customerCareLevelDTO.getCareItems() != null && !customerCareLevelDTO.getCareItems().isEmpty()) {
            for (CustomerCareLevelDTO.CustomerCareItemDTO item : customerCareLevelDTO.getCareItems()) {
                CustomerCare customerCare = new CustomerCare();
                customerCare.setCustomerId(customerCareLevelDTO.getCustomerId());
                customerCare.setCareItemId(item.getCareItemId());
                customerCare.setPurchaseDate(customerCareLevelDTO.getServiceStartDate());
                customerCare.setPurchaseQuantity(item.getQuantity());
                customerCare.setUsedQuantity(0);
                customerCare.setRemainingQuantity(item.getQuantity());
                customerCare.setExpireDate(item.getExpireDate() != null ?
                        item.getExpireDate() : LocalDate.now().plusMonths(3)); // 默认3个月到期
                customerCare.setServiceStatus(ServiceStatus.NORMAL);

                customerCareMapper.insert(customerCare);
            }
        }

        log.info("为客户设置护理级别成功，客户ID: {}, 护理级别ID: {}",
                customerCareLevelDTO.getCustomerId(), customerCareLevelDTO.getCareLevelId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCustomerCareLevel(Long customerId) {
        // 验证客户是否存在
        Customer customer = customerMapper.selectById(customerId);
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }

        if (customer.getCareLevelId() == null) {
            throw new BusinessException("客户没有护理级别");
        }

        // 移除客户护理级别
        Customer updateCustomer = new Customer();
        updateCustomer.setId(customerId);
        updateCustomer.setCareLevelId(null);
        updateCustomer.setCustomerType(CustomerType.SELF_CARE); // 设置为自理老人
        customerMapper.updateById(updateCustomer);

        // 删除客户所有护理项目
        LambdaQueryWrapper<CustomerCare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CustomerCare::getCustomerId, customerId);
        customerCareMapper.delete(wrapper);

        log.info("移除客户护理级别成功，客户ID: {}", customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void purchaseCareItems(Long customerId, List<CustomerCareLevelDTO.CustomerCareItemDTO> careItems) {
        // 验证客户是否存在
        Customer customer = customerMapper.selectById(customerId);
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }

        for (CustomerCareLevelDTO.CustomerCareItemDTO item : careItems) {
            // 检查客户是否已有该护理项目
            LambdaQueryWrapper<CustomerCare> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(CustomerCare::getCustomerId, customerId)
                    .eq(CustomerCare::getCareItemId, item.getCareItemId());
            CustomerCare existCare = customerCareMapper.selectOne(wrapper);

            if (existCare != null) {
                throw new BusinessException("客户已拥有该护理项目，请使用续费功能");
            }

            CustomerCare customerCare = new CustomerCare();
            customerCare.setCustomerId(customerId);
            customerCare.setCareItemId(item.getCareItemId());
            customerCare.setPurchaseDate(LocalDate.now());
            customerCare.setPurchaseQuantity(item.getQuantity());
            customerCare.setUsedQuantity(0);
            customerCare.setRemainingQuantity(item.getQuantity());
            customerCare.setExpireDate(item.getExpireDate() != null ?
                    item.getExpireDate() : LocalDate.now().plusMonths(3));
            customerCare.setServiceStatus(ServiceStatus.NORMAL);

            customerCareMapper.insert(customerCare);
        }

        log.info("客户购买护理项目成功，客户ID: {}, 项目数量: {}", customerId, careItems.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void renewCareService(Long customerCareId, Integer additionalQuantity, LocalDate newExpireDate) {
        CustomerCare customerCare = customerCareMapper.selectById(customerCareId);
        if (customerCare == null) {
            throw new BusinessException("护理服务不存在");
        }

        // 更新数量和到期时间
        CustomerCare updateCare = new CustomerCare();
        updateCare.setId(customerCareId);
        updateCare.setPurchaseQuantity(customerCare.getPurchaseQuantity() + additionalQuantity);
        updateCare.setRemainingQuantity(customerCare.getRemainingQuantity() + additionalQuantity);
        updateCare.setExpireDate(newExpireDate);
        updateCare.setServiceStatus(ServiceStatus.NORMAL); // 续费后状态恢复正常

        customerCareMapper.updateById(updateCare);

        log.info("护理服务续费成功，服务ID: {}, 新增数量: {}", customerCareId, additionalQuantity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeCareService(Long customerCareId) {
        CustomerCare customerCare = customerCareMapper.selectById(customerCareId);
        if (customerCare == null) {
            throw new BusinessException("护理服务不存在");
        }

        customerCareMapper.deleteById(customerCareId);

        log.info("移除护理服务成功，服务ID: {}", customerCareId);
    }

    @Override
    public List<CustomerCareDTO> getAvailableItemsForCustomer(Long customerId, String itemName) {
        return customerCareMapper.selectAvailableItemsForCustomer(customerId, itemName);
    }
}
