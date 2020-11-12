package ${packageName};

/**
 * ${enumVO.enumType.description}
 *
 * @author ${auth}
 */
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

    ${enumVO.enumType.name}(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}