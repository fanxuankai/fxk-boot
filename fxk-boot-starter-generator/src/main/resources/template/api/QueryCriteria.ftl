package ${packageName}.dto;

<#if columns?? &&(columns?size > 0)>
import com.fanxuankai.commons.extra.mybatis.annotation.Query;
</#if>
<#if hasTimestamp || hasDate>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import lombok.Data;
import lombok.experimental.Accessors;

<#if queryHasTimestamp>
import java.sql.Timestamp;
</#if>
<#if queryHasDate>
import java.util.Date;
</#if>
<#if queryHasBigDecimal>
import java.math.BigDecimal;
</#if>
<#if queryHasList>
import java.util.List;
</#if>

/**
 * ${comment} 查询条件
 *
 * @author ${author}
 * @date ${date}
 */
@Data
@Accessors(chain = true)
public class ${className}QueryCriteria {
<#if columns??>
    <#list columns as column>
    /**
     * ${column.remark} ${column.queryType}
        <#if column.fieldType == 'Date' || column.fieldType == 'Timestamp'>
     * GET 请求方式, 日期格式需要改为: MM/dd/yyyy ...
        </#if>
     */
    @Query(field = "${column.fieldName}", type = Query.Type.${column.queryType})
        <#if (column.fieldType == 'Date' || column.fieldType == 'Timestamp')
            && (column.queryType != 'NOT_NULL' || column.queryType != 'IS_NULL'
                || column.queryType != 'ORDER_BY_ASC' || column.queryType != 'ORDER_BY_DESC')>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        </#if>
        <#if column.queryType == 'NOT_NULL' || column.queryType == 'IS_NULL'
            || column.queryType == 'ORDER_BY_ASC' || column.queryType == 'ORDER_BY_DESC'>
    private Boolean ${column.fieldName}${column.capitalQueryType};
        <#elseif column.queryType == 'BETWEEN' || column.queryType == 'NOT_BETWEEN'
            || column.queryType == 'IN' || column.queryType == 'NOT_IN'>
    private List<${column.fieldType}> ${column.fieldName}${column.capitalQueryType};
        <#else>
    private ${column.fieldType} ${column.fieldName}${column.capitalQueryType};
        </#if>
    </#list>
</#if>
}