package com.fanxuankai.boot.enums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.enums.EnumDTO;
import com.fanxuankai.boot.enums.EnumVO;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.mapper.EnumMapper;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@Service
public class EnumServiceImpl extends ServiceImpl<EnumMapper, Enum> implements EnumService {

    @Resource
    private EnumTypeService enumTypeService;

    @Override
    public Optional<EnumVO> find(String typeName) {
        EnumType enumType = enumTypeService.get(typeName);
        if (enumType == null) {
            return Optional.empty();
        }
        List<EnumVO> list = list(Collections.singletonList(typeName));
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    @Override
    public List<EnumVO> list(List<String> typeNames) {
        return listByEnumTypes(enumTypeService.list(typeNames));
    }

    @Override
    public List<EnumVO> all() {
        return listByEnumTypes(enumTypeService.list());
    }

    private List<EnumVO> listByEnumTypes(List<EnumType> enumTypes) {
        if (CollectionUtils.isEmpty(enumTypes)) {
            return Collections.emptyList();
        }
        Map<Long, EnumType> enumTypeMap = enumTypes.stream().collect(Collectors.toMap(EnumType::getId, o -> o));
        return list(new QueryWrapper<Enum>()
                .lambda()
                .eq(Enum::isDisabled, false)
                .in(Enum::getTypeId, enumTypeMap.keySet()))
                .stream()
                .collect(Collectors.groupingBy(Enum::getTypeId))
                .entrySet()
                .stream()
                .map(o -> {
                    EnumType enumType = enumTypeMap.get(o.getKey());
                    o.getValue().sort(Comparator.comparing(Enum::getId));
                    EnumVO enumVO = new EnumVO();
                    enumVO.setEnumType(enumType);
                    enumVO.setEnumList(o.getValue());
                    return enumVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String typeName, Integer code) {
        Optional.ofNullable(enumTypeService.get(typeName))
                .map(EnumType::getId)
                .ifPresent(typeId -> remove(new QueryWrapper<Enum>()
                        .lambda()
                        .eq(Enum::getTypeId, typeId)
                        .eq(Enum::getCode, code)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(EnumDTO dto) {
        saveBatch(enumList(dto.getEnumList(), enumTypeService.addAndGet(dto.getEnumType())));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<EnumDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        List<EnumDTO.EnumType> enumTypes = dtoList.stream()
                .map(EnumDTO::getEnumType)
                .collect(Collectors.toList());
        enumTypeService.batchAdd(enumTypes);
        Map<String, Long> enumTypeMap = enumTypeService.list(Wrappers.lambdaQuery(EnumType.class)
                .in(EnumType::getName, enumTypes.stream()
                        .map(EnumDTO.EnumType::getName)
                        .collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(EnumType::getName, EnumType::getId));
        List<Enum> enumList = dtoList.stream()
                .map(enumDTO -> enumList(enumDTO.getEnumList(),
                        enumTypeMap.get(enumDTO.getEnumType().getName())))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        saveBatch(enumList);
    }

    @Override
    public List<EnumDTO> addIncrement(List<EnumDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return Collections.emptyList();
        }
        List<String> enumTypeNames = dtoList.stream()
                .map(EnumDTO::getEnumType)
                .map(EnumDTO.EnumType::getName)
                .distinct().collect(Collectors.toList());
        Set<String> existsEnumTypeNames =
                enumTypeService.list(enumTypeNames).stream().map(EnumType::getName).collect(Collectors.toSet());
        List<EnumDTO> incrementList =
                dtoList.stream().filter(enumDTO -> !existsEnumTypeNames.contains(enumDTO.getEnumType().getName()))
                        .collect(Collectors.toList());
        add(incrementList);
        return incrementList;
    }

    @Override
    public void add(String typeName, EnumDTO.Enum anEnum) {
        Optional<EnumVO> optional = find(typeName);
        if (!optional.isPresent()) {
            return;
        }
        EnumVO enumVO = optional.get();
        List<Enum> enumList = enumVO.getEnumList();
        if (CollectionUtils.isEmpty(enumList)) {
            return;
        }
        Enum lastEnum = enumList.get(enumList.size() - 1);
        Enum entity = new Enum();
        entity.setTypeId(enumVO.getEnumType().getId());
        entity.setCode(lastEnum.getCode() + 1);
        entity.setName(anEnum.getName());
        entity.setValue(anEnum.getValue());
        save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String typeName) {
        Optional.ofNullable(enumTypeService.get(typeName))
                .map(EnumType::getId)
                .ifPresent(typeId -> {
                    enumTypeService.removeById(typeId);
                    remove(new QueryWrapper<Enum>().lambda().eq(Enum::getTypeId, typeId));
                });
    }

    private List<Enum> enumList(List<EnumDTO.Enum> dtoEnumList, Long typeId) {
        List<Enum> enumList = new ArrayList<>(dtoEnumList.size());
        int lastCode = 0;
        for (EnumDTO.Enum value : dtoEnumList) {
            Enum anEnum = new Enum();
            BeanUtils.copyProperties(value, anEnum);
            anEnum.setTypeId(typeId);
            if (anEnum.getCode() == null) {
                anEnum.setCode(lastCode + 1);
            }
            lastCode = anEnum.getCode();
            enumList.add(anEnum);
        }
        return enumList;
    }
}
