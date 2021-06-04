package ${packageName}.dto;

<#if hasTimestamp || hasDate>
import com.fasterxml.jackson.annotation.JsonFormat;
</#if>
import lombok.Data;
import lombok.experimental.Accessors;

<#if hasNotNull>
import javax.validation.constraints.*;
</#if>
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
 * ${comment} 数据传输对象
 *
 * @author ${author}
 * @date ${date}
 */
@Data
@Accessors(chain = true)
public class ${className}DTO implements Serializable {
<#if columns??>
    <#list columns as column>
        <#if column.remark != ''>
    /**
     * ${column.remark}
     */
        </#if>
        <#if column.notNull>
            <#if column.fieldType = 'String'>
    @NotBlank(message = "${column.remark}不能为空")
            <#else>
    @NotNull(message = "${column.remark}不能为空")
            </#if>
        </#if>
        <#if column.fieldType == 'Date' || column.fieldType == 'Timestamp'>
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        </#if>
    private ${column.fieldType} ${column.fieldName};
    </#list>
</#if>
}