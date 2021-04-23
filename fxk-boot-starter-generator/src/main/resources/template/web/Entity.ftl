package ${packageName}.model;

import com.fanxuankai.commons.extra.mybatis.domain.BaseModel;
<#if fill>
import com.baomidou.mybatisplus.annotation.FieldFill;
</#if>
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
 * ${comment} 实体类
 *
 * @author ${author}
 * @date ${date}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("${tableName}")
public class ${className} extends BaseModel {
<#if columns??>
    <#list columns as column>
        <#if column.remark != ''>
    /**
     * ${column.remark}
     */
        <#else>
    /**
     * ${column.fieldName}
     */
        </#if>
        <#if column.primaryKey>
    @TableId(value = "${column.columnName}", <#if auto>type = IdType.AUTO<#else>type = IdType.ASSIGN_ID</#if>)
        <#else>
    @TableField(value = "${column.columnName}"<#if column.fill??>, fill = FieldFill.${column.fill}</#if>)
        </#if>
    private ${column.fieldType} ${column.fieldName};

    </#list>
</#if>
}