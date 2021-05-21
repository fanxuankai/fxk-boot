package ${packageName}.vo;

<#if hasTimestamp || hasDate || hasLong>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
<#if hasTimestamp>
import java.sql.Timestamp;
</#if>
<#if hasDate>
import java.util.Date;
</#if>
<#if hasBigDecimal>
import java.math.BigDecimal;
</#if>

/**
 * ${comment} 视图对象
 *
 * @author ${author}
 * @date ${date}
 */
@Data
@Accessors(chain = true)
public class ${className}VO implements Serializable {
<#if columns??>
    <#list columns as column>
        <#if column.remark != ''>
    /**
     * ${column.remark}
     */
        </#if>
        <#if column.fieldType == 'Date' || column.fieldType == 'Timestamp'>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        <#elseif column.fieldType == 'Long'>
    @JsonFormat(shape = JsonFormat.Shape.STRING)
        </#if>
    private ${column.fieldType} ${column.fieldName};

    </#list>
</#if>
}