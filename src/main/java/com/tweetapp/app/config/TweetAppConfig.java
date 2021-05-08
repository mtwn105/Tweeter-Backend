package com.tweetapp.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


/**
 * Configuration for API Documentation using Swagger
 */
@Configuration
@EnableSwagger2
public class TweetAppConfig {

    @Bean
    public Docket orderApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tweetapp.app.controller"))
                .paths(PathSelectors.regex("/api/v1.0.*"))
                .build()
                .apiInfo(metadata());
    }

    private ApiInfo metadata() {
        return new ApiInfo("Tweet App API", "API for user management and interacting with tweets", "1.0", "", new Contact("Amit Wani", null, null),
                "gpl", null, new ArrayList<>());
    }
}
