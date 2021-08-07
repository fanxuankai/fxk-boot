package com.fanxuankai.boot.enums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanxuankai.boot.enums.EnumDTO;
import com.fanxuankai.boot.enums.EnumVO;
import com.fanxuankai.boot.enums.domain.Enum;

import java.util.List;
import java.util.Optional;

/**
 * @author fanxuankai
 */
public interface EnumService extends IService<Enum> {
    /**
     * 批量新增枚举类型
     *
     * @param dtoList 枚举数据
     */
    void add(List<EnumDTO> dtoList);

    /**
     * 增加枚举
     *
     * @param typeName 枚举类名
     * @param anEnum   枚举
     */
    void add(String typeName, Enum anEnum);

    /**
     * 查枚举
     *
     * @param typeName 枚举类名
     * @return Optional 没有查找到返回 empty
     */
    Optional<EnumVO> find(String typeName);

    /**
     * 批量查枚举
     *
     * @param typeNames 枚举类名
     * @return list
     */
    List<EnumVO> list(List<String> typeNames);

    /**
     * 批量查枚举
     *
     * @param typeNames        枚举类名
     * @param generateDataOnly 只生成数据
     * @return list
     */
    List<EnumVO> list(List<String> typeNames, boolean generateDataOnly);

    /**
     * 查所有枚举
     *
     * @return list
     */
    List<EnumVO> all();

    /**
     * 删除枚举
     *
     * @param typeName 枚举类名
     * @param code     枚举代码
     */
    void delete(String typeName, Integer code);

    /**
     * 删除枚举类型
     *
     * @param typeName 枚举类名
     */
    void delete(String typeName);

    /**
     * 删除枚举类型
     *
     * @param typeNames 枚举类名
     */
    void delete(List<String> typeNames);

    /**
     * 设置 code
     *
     * @param dtoList 枚举数据
     */
    default void setupCode(List<EnumDTO> dtoList) {
        for (EnumDTO enumDTO : dtoList) {
            int lastCode = -1;
            for (Enum value : enumDTO.getEnumList()) {
                if (value.getCode() == null) {
                    value.setCode(lastCode + 1);
                }
                lastCode = value.getCode();
            }
        }
    }
}
