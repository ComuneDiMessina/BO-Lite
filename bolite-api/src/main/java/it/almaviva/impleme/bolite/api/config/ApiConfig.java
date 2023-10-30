package it.almaviva.impleme.bolite.api.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class   ApiConfig {

    public static final String AUTHORIZATION_HEADER = "X-Auth-Token";

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
         .securityContexts(Lists.newArrayList(securityContext()))
         .securitySchemes(Lists.newArrayList(apiKey(), apiKey2()));


        docket = docket.select()
                .apis(RequestHandlerSelectors.basePackage("it.almaviva.impleme.bolite.api.controllers"))
                .paths(PathSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build() .protocols(Sets.newHashSet("http", "https"));

        return docket;
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ImpleME-BOLite")
                .description("API for Back Office")
                .version("2.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, "header");
    }

    private ApiKey apiKey2() {
        return new ApiKey("Bearer", "Authorization", "header");
    }


    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }


    List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(
                new SecurityReference(AUTHORIZATION_HEADER,  new AuthorizationScope[0]),
                new SecurityReference("Bearer",  new AuthorizationScope[0]));
    }

}