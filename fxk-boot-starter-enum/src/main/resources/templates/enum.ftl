package ${packageName};

import com.fanxuankai.commons.util.EnumProtocol;

/**
 * ${enumVO.enumType.description}
 *
 * @author ${auth}
 */
public enum ${enumVO.enumType.name} implements EnumProtocol {
    <#list enumVO.enumList as anEnum>
    /**
     * ${anEnum.value}
     */
    ${anEnum.name}(${anEnum.code}, "${anEnum.value}"<#if hasDescription>, "${anEnum.description}"</#if>),
    </#list>
    ;
    private final Integer code;
    private final String value;
    <#if hasDescription>
    private final String description;
    </#if>

    ${enumVO.enumType.name}(Integer code, String value<#if hasDescription>, String description</#if>) {
        this.code = code;
        this.value = value;
        <#if hasDescription>
        this.description = description;
        </#if>
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    <#if hasDescription>
    @Override
    public String getDescription() {
        return this.description;
    }
    </#if>
}