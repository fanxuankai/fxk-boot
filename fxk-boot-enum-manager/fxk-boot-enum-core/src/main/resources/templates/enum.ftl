package ${packageName};

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ${enumVO.enumType.description}
 *
 * @author ${auth}
 */
@AllArgsConstructor
@Getter
public enum ${enumVO.enumType.name} {
    <#list enumVO.enumList as anEnum>
    /**
     * ${anEnum.value}
     */
    ${anEnum.name}(${anEnum.code}, "${anEnum.value}"),
    </#list>
    ;
    private final Integer code;
    private final String value;
}