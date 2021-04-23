package com.fanxuankai.boot.springfox;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author fanxuankai
 */
@ConfigurationProperties(SwaggerProperties.PREFIX)
public class SwaggerProperties {

    public static final String PREFIX = "swagger";

    @NestedConfigurationProperty
    private DocketConfiguration docket;

    public DocketConfiguration getDocket() {
        return docket;
    }

    public void setDocket(DocketConfiguration docket) {
        this.docket = docket;
    }
}
