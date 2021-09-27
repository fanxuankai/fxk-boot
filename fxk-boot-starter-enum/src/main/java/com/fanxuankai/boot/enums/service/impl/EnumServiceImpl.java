package com.fanxuankai.boot.enums.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanxuankai.boot.enums.EnumDTO;
import com.fanxuankai.boot.enums.EnumVO;
import com.fanxuankai.boot.enums.domain.Enum;
import com.fanxuankai.boot.enums.domain.EnumType;
import com.fanxuankai.boot.enums.mapper.EnumMapper;
import com.fanxuankai.boot.enums.service.EnumService;
import com.fanxuankai.boot.enums.service.EnumTypeService;
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
    public List<EnumVO> list(List<String> typeNames, boolean generateDataOnly) {
        return listByEnumTypes(enumTypeService.list(Wrappers.lambdaQuery(EnumType.class)
                .in(EnumType::getName, typeNames)
                .eq(EnumType::isGenerateDataOnly, generateDataOnly)
        ));
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
        return list(Wrappers.lambdaQuery(Enum.class)
                .eq(Enum::isDisabled, false)
                .in(Enum::getTypeId, enumTypeMap.keySet()))
                .stream()
                .collect(Collectors.groupingBy(Enum::getTypeId))
                .entrySet()
                .stream()
                .map(o -> {
                    EnumType enumType = enumTypeMap.get(o.getKey());
                    o.getValue().sort(Comparator.comparing(Enum::getSort, Comparator.nullsLast(Integer::compareTo)));
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
                .ifPresent(typeId -> remove(Wrappers.lambdaQuery(Enum.class)
                        .eq(Enum::getTypeId, typeId)
                        .eq(Enum::getCode, code)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(List<EnumDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        List<EnumType> enumTypes = dtoList.stream()
                .map(EnumDTO::getEnumType)
                .collect(Collectors.toList());
        enumTypeService.batchAdd(enumTypes);
        Map<String, Long> enumTypeMap = enumTypeService.list(Wrappers.lambdaQuery(EnumType.class)
                .in(EnumType::getName, enumTypes.stream()
                        .map(EnumType::getName)
                        .collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(EnumType::getName, EnumType::getId));
        List<Enum> enumList = dtoList.stream()
                .peek(o -> {
                    Long typeId = enumTypeMap.get(o.getEnumType().getName());
                    o.getEnumList().forEach(anEnum -> anEnum.setTypeId(typeId));
                })
                .map(EnumDTO::getEnumList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        saveBatch(enumList);
    }

    @Override
    public void add(String typeName, Enum anEnum) {
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
                    remove(Wrappers.lambdaQuery(Enum.class)
                            .eq(Enum::getTypeId, typeId));
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<String> typeNames) {
        if (CollectionUtils.isEmpty(typeNames)) {
            return;
        }
        List<EnumType> types = enumTypeService.list(Wrappers.lambdaQuery(EnumType.class)
                .in(EnumType::getName, typeNames));
        if (types.isEmpty()) {
            return;
        }
        List<Long> typeIds = types.stream().map(EnumType::getId).collect(Collectors.toList());
        enumTypeService.removeByIds(typeIds);
        remove(Wrappers.lambdaQuery(Enum.class).in(Enum::getTypeId, typeIds));
    }
}
