package br.com.hebert.citymanager.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.config.baseUrl}")
    private String baseUrl;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(baseUrl))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiMetaData());
    }
    private ApiInfo apiMetaData() {
        return new ApiInfoBuilder()
                .title("City Manager")
                .description("City Manager Challenge")
                .version("1.0.0")
                .build();
    }
    
}

