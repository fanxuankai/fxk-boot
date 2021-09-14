package ${packageName}.dto;

<#if columns?? &&(columns?size > 0)>
import com.fanxuankai.commons.extra.mybatis.annotation.Query;
</#if>
import lombok.Data;
import lombok.experimental.Accessors;
<#if hasTimestamp || hasDate>
import org.springframework.format.annotation.DateTimeFormat;
</#if>

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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