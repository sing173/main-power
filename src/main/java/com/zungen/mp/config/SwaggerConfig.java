package com.zungen.mp.config;

import com.zungen.mp.common.config.BaseSwaggerConfig;
import com.zungen.mp.common.domain.SwaggerProperties;
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
                .apiBasePackage("com.zungen.aihall.modules")
                .title("智能主站-演示")
                .description("继电主站演示项目相关接口文档")
                .contactName("zungen")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
