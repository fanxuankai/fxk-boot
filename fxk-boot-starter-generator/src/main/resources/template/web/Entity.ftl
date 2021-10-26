package ${packageName}.model;

<#if fill>
import com.baomidou.mybatisplus.annotation.FieldFill;
</#if>
<#if hasPrimaryKey>
import com.baomidou.mybatisplus.annotation.IdType;
</#if>
<#if fill>
import com.baomidou.mybatisplus.annotation.TableField;
</#if>
<#if hasPrimaryKey>
import com.baomidou.mybatisplus.annotation.TableId;
</#if>
<#if inherit>
import com.fanxuankai.commons.extra.mybatis.base.BaseModel;
</#if>
import lombok.Data;
<#if inherit>
import lombok.EqualsAndHashCode;
</#if>
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
 */
<#if inherit>
@EqualsAndHashCode(callSuper = true)
</#if>
@Data
@Accessors(chain = true)
public class ${className}<#if inherit> extends BaseModel</#if> {
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
    @TableId(type = <#if column.extra == 'auto_increment'>IdType.AUTO<#else>IdType.ASSIGN_ID</#if>)
        </#if>
        <#if column.fill??>
    @TableField(fill = FieldFill.${column.fill})
        </#if>
    private ${column.fieldType} ${column.fieldName};
    </#list>
</#if>
}