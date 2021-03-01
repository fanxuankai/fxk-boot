package com.fanxuankai.boot.mybatis.plus.core.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Page 工具类
 *
 * @author fanxuankai
 */
public class PageHelper {

    private static final IPage<?> EMPTY_PAGE = new Page<>();

    /**
     * 空的 Page 对象
     *
     * @param <T> record 类型
     * @return Page
     */
    @SuppressWarnings("unchecked")
    public static <T> IPage<T> emptyPage() {
        return (IPage<T>) EMPTY_PAGE;
    }

    /**
     * 转换分页记录对象类型, 比如把实体类转为vo
     *
     * @param page     分页数据
     * @param function 转换方法
     * @param <T>      转换前类型
     * @param <R>      转换后类型
     * @return IPage
     */
    public static <T, R> IPage<R> convert(IPage<T> page, Function<T, R> function) {
        return new Page<R>(page.getCurrent(), page.getSize())
                .setRecords(page.getRecords()
                        .stream()
                        .map(function)
                        .collect(Collectors.toList())
                );
    }

}