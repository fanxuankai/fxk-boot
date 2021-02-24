package com.fanxuankai.boot.mybatis.plus.core.annotation;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author fanxuankai
 */
public class QueryHelp {

    /**
     * 查询条件类转 QueryWrapper
     *
     * @param entityClass 实体类
     * @param criteria    查询条件类
     * @param <T>         /
     * @param <C>         /
     * @return /
     */
    public static <T, C> QueryWrapper<T> getQueryWrapper(Class<T> entityClass, C criteria) {
        if (criteria == null) {
            return Wrappers.emptyWrapper();
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        init(wrapper, entityClass, criteria);
        return wrapper;
    }

    /**
     * 查询条件类转 UpdateWrapper
     *
     * @param entityClass 实体类
     * @param criteria    查询条件类
     * @param <T>         /
     * @param <C>         /
     * @return /
     */
    public static <T, C> UpdateWrapper<T> getUpdateWrapper(Class<T> entityClass, C criteria) {
        UpdateWrapper<T> wrapper = new UpdateWrapper<>();
        if (criteria == null) {
            return wrapper;
        }
        init(wrapper, entityClass, criteria);
        return wrapper;
    }

    private static <T, C> void init(AbstractWrapper<T, String, ?> wrapper, Class<T> entityClass, C criteria) {
        Map<String, ColumnCache> columnMap = LambdaUtils.getColumnMap(entityClass);
        List<Field> fields = getAllFields(criteria.getClass(), new ArrayList<>());
        for (Field field : fields) {
            field.setAccessible(true);
            Query q = field.getAnnotation(Query.class);
            if (q == null) {
                continue;
            }
            String propName = q.field();
            String fuzzy = q.fuzzy();
            String attributeName = StringUtils.isBlank(propName) ? field.getName() : propName;
            Object val = ReflectionKit.getMethodValue(criteria, field.getName());
            if (ObjectUtils.isEmpty(val)) {
                continue;
            }
            if (StringUtils.isNotBlank(fuzzy)) {
                for (String s : fuzzy.split(StringPool.COMMA)) {
                    wrapper.and(theWrapper -> {
                        ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(s));
                        theWrapper.like(columnCache.getColumn(), val);
                    });
                }
                continue;
            }
            ColumnCache columnCache = columnMap.get(LambdaUtils.formatKey(attributeName));
            String column = columnCache.getColumn();
            setupWrapper(wrapper, q, column, val);
        }
    }

    private static <T> void setupWrapper(AbstractWrapper<T, String, ?> wrapper, Query q, String column, Object val) {
        switch (q.type()) {
            case EQ:
                wrapper.eq(column, val);
                break;
            case GE:
                wrapper.ge(column, val);
                break;
            case LE:
                wrapper.le(column, val);
                break;
            case LIKE:
                wrapper.like(column, val);
                break;
            case LIKE_LEFT:
                wrapper.likeLeft(column, val);
                break;
            case LIKE_RIGHT:
                wrapper.likeRight(column, val);
                break;
            case GT:
                wrapper.gt(column, val);
                break;
            case LT:
                wrapper.lt(column, val);
                break;
            case IN:
                if (val instanceof Collection) {
                    wrapper.in(column, (Collection<?>) val);
                }
                break;
            case NE:
                wrapper.ne(column, val);
                break;
            case BETWEEN:
                if (val instanceof Collection) {
                    List<?> between = new ArrayList<>((Collection<?>) val);
                    wrapper.between(column, between.get(0), between.get(1));
                }
                break;
            case NOT_NULL:
                wrapper.isNotNull(column);
                break;
            case IS_NULL:
                wrapper.isNull(column);
                break;
            default:
                break;
        }
    }

    private static List<Field> getAllFields(Class<?> clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
