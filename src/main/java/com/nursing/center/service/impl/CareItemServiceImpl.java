package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:37
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.dto.CareItemQueryDTO;
import com.nursing.center.entity.CareItem;
import com.nursing.center.entity.CareLevelItem;
import com.nursing.center.mapper.CareItemMapper;
import com.nursing.center.mapper.CareLevelItemMapper;
import com.nursing.center.service.CareItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareItemServiceImpl implements CareItemService {

    private final CareItemMapper careItemMapper;
    private final CareLevelItemMapper careLevelItemMapper;

    @Override
    public IPage<CareItemDTO> getCareItemPage(CareItemQueryDTO query) {
        Page<CareItem> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<CareItem> wrapper = new LambdaQueryWrapper<>();
        if (query.getStatus() != null) {
            wrapper.eq(CareItem::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getItemName())) {
            wrapper.like(CareItem::getItemName, query.getItemName());
        }
        wrapper.orderByDesc(CareItem::getCreateTime);

        IPage<CareItem> itemPage = careItemMapper.selectPage(page, wrapper);

        // 转换为DTO
        IPage<CareItemDTO> dtoPage = new Page<>();
        BeanUtils.copyProperties(itemPage, dtoPage, "records");

        List<CareItemDTO> dtoList = itemPage.getRecords().stream()
                .map(item -> {
                    CareItemDTO dto = new CareItemDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                })
                .collect(Collectors.toList());

        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    public CareItemDTO getCareItemById(Long id) {
        CareItem careItem = careItemMapper.selectById(id);
        if (careItem == null) {
            return null;
        }

        CareItemDTO dto = new CareItemDTO();
        BeanUtils.copyProperties(careItem, dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCareItem(CareItemDTO careItemDTO) {
        // 检查项目编号是否已存在
        checkItemCodeExists(careItemDTO.getItemCode(), null);

        CareItem careItem = new CareItem();
        BeanUtils.copyProperties(careItemDTO, careItem);
        careItem.setStatus(1); // 默认启用

        careItemMapper.insert(careItem);

        log.info("新增护理项目成功，ID: {}, 名称: {}", careItem.getId(), careItem.getItemName());
        return careItem.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCareItem(CareItemDTO careItemDTO) {
        // 验证护理项目是否存在
        CareItem existItem = careItemMapper.selectById(careItemDTO.getId());
        if (existItem == null) {
            throw new BusinessException("护理项目不存在");
        }

        // 检查项目编号是否已存在（排除自己）
        checkItemCodeExists(careItemDTO.getItemCode(), careItemDTO.getId());

        CareItem careItem = new CareItem();
        BeanUtils.copyProperties(careItemDTO, careItem);

        // 如果状态修改为停用，需要从护理级别中移除
        if (careItem.getStatus() == 0 && existItem.getStatus() == 1) {
            removeCareItemFromLevels(careItem.getId());
        }

        careItemMapper.updateById(careItem);

        log.info("更新护理项目成功，ID: {}, 名称: {}", careItem.getId(), careItem.getItemName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCareItem(Long id) {
        CareItem careItem = careItemMapper.selectById(id);
        if (careItem == null) {
            throw new BusinessException("护理项目不存在");
        }

        // 从护理级别中移除该项目
        removeCareItemFromLevels(id);

        // 逻辑删除护理项目
        careItemMapper.deleteById(id);

        log.info("删除护理项目成功，ID: {}, 名称: {}", id, careItem.getItemName());
    }

    @Override
    public List<CareItemDTO> getEnabledCareItems() {
        LambdaQueryWrapper<CareItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CareItem::getStatus, 1);
        wrapper.orderByDesc(CareItem::getCreateTime);

        List<CareItem> items = careItemMapper.selectList(wrapper);
        return items.stream()
                .map(item -> {
                    CareItemDTO dto = new CareItemDTO();
                    BeanUtils.copyProperties(item, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 检查护理项目编号是否已存在
     */
    private void checkItemCodeExists(String itemCode, Long excludeId) {
        LambdaQueryWrapper<CareItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CareItem::getItemCode, itemCode);
        if (excludeId != null) {
            wrapper.ne(CareItem::getId, excludeId);
        }

        CareItem existItem = careItemMapper.selectOne(wrapper);
        if (existItem != null) {
            throw new BusinessException("护理项目编号已存在");
        }
    }

    /**
     * 从所有护理级别中移除指定护理项目
     */
    private void removeCareItemFromLevels(Long careItemId) {
        LambdaQueryWrapper<CareLevelItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CareLevelItem::getCareItemId, careItemId);
        careLevelItemMapper.delete(wrapper);
    }
}
