package ${packageName}.dao;

<#if hasUnique>
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
</#if>
import ${packageName}.dto.${className}QueryCriteria;
import ${packageName}.model.${className};
import com.fanxuankai.commons.extra.mybatis.base.BaseDao;

/**
 * ${comment} 数据访问接口
 *
 * @author ${author}
 * @date ${date}
 */
public interface ${className}Dao extends BaseDao<${className}, ${className}QueryCriteria> {
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
}