package com.fanxuankai.boot.generator.strategy.enums;

/**
 * @author fanxuankai
 */
public enum TemplateFile {
    /**
     * /
     */
    API("api/Api"),
    DTO("api/Dto"),
    QUERY_CRITERIA("api/QueryCriteria"),
    VO("api/Vo"),
    API_IMPL("web/ApiImpl"),
    CONTROLLER("web/Controller"),
    DAO("web/Dao"),
    DAO_IMPL("web/DaoImpl"),
    ENTITY("web/Entity"),
    MAPPER("web/Mapper"),
    MAP_STRUCT_MAPPER("web/MapStructMapper"),
    SERVICE("web/Service"),
    SERVICE_IMPL("web/ServiceImpl"),
    ;
    /**
     * 模板名
     */
    private final String name;

    TemplateFile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}