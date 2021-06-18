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
    ${anEnum.name}(${anEnum.code}, "${anEnum.value}"),
    </#list>
    ;
    private final int code;
    private final String value;

    ${enumVO.enumType.name}(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}