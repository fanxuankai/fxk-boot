package ${packageName}.dao;

import com.baomidou.mybatisplus.extension.service.IService;
<#if hasUnique>
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
</#if>
import com.fanxuankai.commons.extra.mybatis.util.QueryHelper;
import com.fanxuankai.commons.extra.mybatis.util.MybatisPlusPageUtils;
import com.fanxuankai.commons.domain.Page;
import com.fanxuankai.commons.domain.PageResult;
import com.fanxuankai.commons.domain.StatusEnum;
import com.fanxuankai.commons.exception.ApiException;
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.model.${className};

import java.util.List;
import java.util.Optional;

/**
 * ${comment} 数据访问接口
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${className}Dao extends IService<${className}> {
    /**
     * 根据 ID 查询
     *
     * @param ${pkFieldName} ID
     * @return ${className}, 查询不到会抛出异常
     */
    default ${className} getOne(${pkCapitalFieldType} ${pkFieldName}) {
        return Optional.ofNullable(getById(${pkFieldName})).orElseThrow(() -> new ApiException(StatusEnum.DATA_NOT_FOUND));
    }

	/**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param page    分页参数
     * @return PageResult
     */
    default PageResult<${className}> page(${className}QueryCriteria criteria, Page page) {
    	return MybatisPlusPageUtils.convert(page(MybatisPlusPageUtils.convert(page), QueryHelper.getQueryWrapper(${className}.class, criteria)));
    }

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List
     */
    default List<${className}> list(${className}QueryCriteria criteria) {
    	return list(QueryHelper.getQueryWrapper(${className}.class, criteria));
    }

    /**
     * 查询数量
     *
     * @param criteria 条件参数
     * @return int
     */
    default int count(${className}QueryCriteria criteria) {
        return count(QueryHelper.getQueryWrapper(${className}.class, criteria));
    }

<#if columns??>
    <#list columns as column>
        <#if column.unique>
    /**
     * 根据 ${column.capitalFieldName} 查询
     *
     * @param ${column.columnName} /
     * @return /
     */
    default ${className} findBy${column.capitalFieldName}(${column.fieldType} ${column.columnName}) {
        return getOne(Wrappers.lambdaQuery(${className}.class).eq(${className}::get${column.capitalFieldName}, ${column.columnName}), false);
    }

        </#if>
    </#list>
</#if>
    /**
     * 更新
     *
     * @param ${changeClassName} 实体类
     * @param criteria 条件参数
     * @return 是否更新成功
     */
    default boolean update(${className} ${changeClassName}, ${className}QueryCriteria criteria) {
        return update(${changeClassName}, QueryHelper.getUpdateWrapper(${className}.class, criteria));
    }

    /**
     * 删除
     *
     * @param criteria 条件参数
     * @return 是否删除成功
     */
    default boolean remove(${className}QueryCriteria criteria) {
        return remove(QueryHelper.getQueryWrapper(${className}.class, criteria));
    }
}