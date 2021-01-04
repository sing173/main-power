package com.shinetech.aihall.config;

import com.shinetech.aihall.common.config.BaseSwaggerConfig;
import com.shinetech.aihall.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by admin on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.shinetech.aihall.modules")
                .title("智慧厅堂")
                .description("双照智慧厅堂项目相关接口文档")
                .contactName("shinetech")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
