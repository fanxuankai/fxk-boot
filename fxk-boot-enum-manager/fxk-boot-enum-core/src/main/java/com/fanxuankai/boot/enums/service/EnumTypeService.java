package com.fanxuankai.boot.enums.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.boot.enums.EnumDTO;
import com.fanxuankai.boot.enums.domain.EnumType;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
public interface EnumTypeService extends IService<EnumType> {

    /**
     * 查枚举类型
     *
     * @param name 枚举类名
     * @return 枚举类型
     */
    default EnumType get(String name) {
        return getOne(new QueryWrapper<EnumType>().lambda().eq(EnumType::getName, name));
    }

    /**
     * 批量查枚举类型
     *
     * @param names 枚举类名
     * @return 枚举类型
     */
    default List<EnumType> list(List<String> names) {
        return list(new QueryWrapper<EnumType>().lambda().in(EnumType::getName, names));
    }

    /**
     * 新增枚举类型
     *
     * @param enumType 枚举类型
     */
    default void add(EnumDTO.EnumType enumType) {
        save(new EnumType()
                .setName(enumType.getName())
                .setDescription(enumType.getDescription()));
    }

    /**
     * 批量新增枚举类型
     *
     * @param enumTypes 枚举类型
     */
    default void batchAdd(List<EnumDTO.EnumType> enumTypes) {
        if (CollectionUtils.isEmpty(enumTypes)) {
            return;
        }
        saveBatch(enumTypes.stream().map(enumType ->
                new EnumType()
                        .setName(enumType.getName())
                        .setDescription(enumType.getDescription())).collect(Collectors.toList()));
    }

    /**
     * 新增枚举类型
     *
     * @param enumType 枚举描述
     * @return 返回枚举类型 id
     */
    default Long addAndGet(EnumDTO.EnumType enumType) {
        add(enumType);
        return get(enumType.getName()).getId();
    }

    /**
     * 删除枚举类型
     *
     * @param name 枚举类名
     */
    default void delete(String name) {
        remove(new QueryWrapper<EnumType>().lambda().eq(EnumType::getName, name));
    }
}
