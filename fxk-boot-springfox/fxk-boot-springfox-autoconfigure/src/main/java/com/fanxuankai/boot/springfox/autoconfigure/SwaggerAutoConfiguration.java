package com.fanxuankai.boot.springfox.autoconfigure;

import io.swagger.models.auth.In;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author fanxuankai
 */
@EnableOpenApi
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = SwaggerProperties.PREFIX, name = "docket.enabled", havingValue = "true")
public class SwaggerAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket createRestApi() throws ClassNotFoundException {
        DocketConfiguration docket = swaggerProperties.getDocket();
        return new Docket(DocumentationType.OAS_30)
                .pathMapping("/")
                // 开关
                .enable(Objects.equals(Boolean.TRUE, docket.getEnabled()))
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                .host(docket.getHost())
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(apis())
                .paths(PathSelectors.any())
                .build()
                // 支持的通讯协议集合
                .protocols(newHashSet("https", "http"))
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())
                // 授权信息全局应用
                .securityContexts(securityContexts());
    }

    @SuppressWarnings("unchecked")
    private Predicate<RequestHandler> apis() throws ClassNotFoundException {
        ApisConfiguration apis = swaggerProperties.getDocket().getApis();
        if (apis == null) {
            return RequestHandlerSelectors.any();
        }
        if (StringUtils.hasText(apis.getBasePackage())) {
            return RequestHandlerSelectors.basePackage(apis.getBasePackage());
        }
        if (StringUtils.hasText(apis.getClassAnnotation())) {
            Class<? extends Annotation> classAnnotation =
                    (Class<? extends Annotation>) ClassUtils.forName(apis.getClassAnnotation(),
                            this.getClass().getClassLoader());
            return RequestHandlerSelectors.withClassAnnotation(classAnnotation);
        }
        if (StringUtils.hasText(apis.getMethodAnnotation())) {
            Class<? extends Annotation> methodAnnotation =
                    (Class<? extends Annotation>) ClassUtils.forName(apis.getMethodAnnotation(),
                            this.getClass().getClassLoader());
            return RequestHandlerSelectors.withMethodAnnotation(methodAnnotation);
        }
        return RequestHandlerSelectors.any();
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        ApiInfoConfiguration apiInfo = swaggerProperties.getDocket().getApiInfo();
        if (apiInfo == null) {
            return null;
        }
        return new ApiInfoBuilder().title(apiInfo.getName() + " Api Doc")
                .description(apiInfo.getDescription())
                .contact(new Contact("lighter", null, "fanxuankai89@gmail.com"))
                .version("Application Version: " + apiInfo.getVersion() + ", Spring Boot Version" +
                        ": " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        List<String> headers = swaggerProperties.getDocket().getHeaders();
        if (headers == null) {
            return Collections.emptyList();
        }
        return headers
                .stream()
                .map(s -> new ApiKey(s, s, In.HEADER.toValue()))
                .collect(Collectors.toList());
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        List<String> headers = swaggerProperties.getDocket().getHeaders();
        if (headers == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(
                SecurityContext.builder().securityReferences(headers
                        .stream()
                        .map(s -> new SecurityReference(s, new AuthorizationScope[]{new AuthorizationScope("global",
                                "")}))
                        .collect(Collectors.toList()))
                        .build()
        );
    }

    @SafeVarargs
    private final <T> Set<T> newHashSet(T... ts) {
        if (ts.length > 0) {
            return new LinkedHashSet<>(Arrays.asList(ts));
        }
        return null;
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     */
    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
        }).excludePathPatterns("/swagger**/**", "/webjars/**", "/v3/**", "/doc.html");
    }

}
