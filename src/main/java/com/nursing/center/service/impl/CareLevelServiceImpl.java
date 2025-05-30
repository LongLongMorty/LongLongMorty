package com.nursing.center.service.impl;

/**
 * @BelongsProject: nursing-center-system
 * @BelongsPackage: com.nursing.center.service.impl
 * @Author: LongLongMorty
 * @CreateTime: 2025-05-29  13:35
 * @Description: TODO
 * @Version: 1.0
 */
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nursing.center.common.exception.BusinessException;
import com.nursing.center.dto.CareLevelDTO;
import com.nursing.center.dto.CareLevelQueryDTO;
import com.nursing.center.dto.CareItemDTO;
import com.nursing.center.entity.CareLevel;
import com.nursing.center.entity.CareLevelItem;
import com.nursing.center.mapper.CareLevelItemMapper;
import com.nursing.center.mapper.CareLevelMapper;
import com.nursing.center.service.CareLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CareLevelServiceImpl implements CareLevelService {

    private final CareLevelMapper careLevelMapper;
    private final CareLevelItemMapper careLevelItemMapper;

    @Override
    public IPage<CareLevelDTO> getCareLevelPage(CareLevelQueryDTO query) {
        Page<CareLevelDTO> page = new Page<>(query.getPageNum(), query.getPageSize());
        return careLevelMapper.selectCareLevelPage(page, query);
    }

    @Override
    public CareLevelDTO getCareLevelById(Long id) {
        return careLevelMapper.selectCareLevelWithItems(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addCareLevel(CareLevelDTO careLevelDTO) {
        // 检查编码是否已存在
        checkLevelCodeExists(careLevelDTO.getLevelCode(), null);

        CareLevel careLevel = new CareLevel();
        BeanUtils.copyProperties(careLevelDTO, careLevel);
        careLevel.setStatus(1); // 默认启用

        careLevelMapper.insert(careLevel);

        log.info("新增护理级别成功，ID: {}, 名称: {}", careLevel.getId(), careLevel.getLevelName());
        return careLevel.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCareLevel(CareLevelDTO careLevelDTO) {
        // 验证护理级别是否存在
        CareLevel existLevel = careLevelMapper.selectById(careLevelDTO.getId());
        if (existLevel == null) {
            throw new BusinessException("护理级别不存在");
        }

        // 检查编码是否已存在（排除自己）
        checkLevelCodeExists(careLevelDTO.getLevelCode(), careLevelDTO.getId());

        CareLevel careLevel = new CareLevel();
        BeanUtils.copyProperties(careLevelDTO, careLevel);

        careLevelMapper.updateById(careLevel);

        log.info("更新护理级别成功，ID: {}, 名称: {}", careLevel.getId(), careLevel.getLevelName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCareLevel(Long id) {
        CareLevel careLevel = careLevelMapper.selectById(id);
        if (careLevel == null) {
            throw new BusinessException("护理级别不存在");
        }

        // 逻辑删除护理级别
        careLevelMapper.deleteById(id);

        // 删除护理级别项目关联
        LambdaQueryWrapper<CareLevelItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CareLevelItem::getCareLevelId, id);
        careLevelItemMapper.delete(wrapper);

        log.info("删除护理级别成功，ID: {}, 名称: {}", id, careLevel.getLevelName());
    }

    @Override
    public List<CareItemDTO> getItemsByLevelId(Long careLevelId) {
        return careLevelItemMapper.selectItemsByLevelId(careLevelId);
    }

    @Override
    public List<CareItemDTO> getAvailableItemsForLevel(Long careLevelId) {
        return careLevelItemMapper.selectAvailableItemsNotInLevel(careLevelId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void configureItems(Long careLevelId, List<Long> careItemIds) {
        // 验证护理级别是否存在
        CareLevel careLevel = careLevelMapper.selectById(careLevelId);
        if (careLevel == null) {
            throw new BusinessException("护理级别不存在");
        }

        // 批量添加护理项目关联
        List<CareLevelItem> careLevelItems = careItemIds.stream()
                .map(itemId -> {
                    CareLevelItem item = new CareLevelItem();
                    item.setCareLevelId(careLevelId);
                    item.setCareItemId(itemId);
                    return item;
                })
                .collect(Collectors.toList());

        careLevelItems.forEach(careLevelItemMapper::insert);

        log.info("护理级别配置项目成功，级别ID: {}, 项目数量: {}", careLevelId, careItemIds.size());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeItems(Long careLevelId, List<Long> careItemIds) {
        careLevelItemMapper.deleteByCareItemIds(careLevelId, careItemIds);
        log.info("护理级别移除项目成功，级别ID: {}, 项目数量: {}", careLevelId, careItemIds.size());
    }

    @Override
    public List<CareLevelDTO> getEnabledCareLevels() {
        return careLevelMapper.selectEnabledCareLevels();
    }

    /**
     * 检查护理级别编码是否已存在
     */
    private void checkLevelCodeExists(String levelCode, Long excludeId) {
        LambdaQueryWrapper<CareLevel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CareLevel::getLevelCode, levelCode);
        if (excludeId != null) {
            wrapper.ne(CareLevel::getId, excludeId);
        }

        CareLevel existLevel = careLevelMapper.selectOne(wrapper);
        if (existLevel != null) {
            throw new BusinessException("护理级别编码已存在");
        }
    }
}
