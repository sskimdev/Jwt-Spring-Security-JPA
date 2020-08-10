package com.bithumbhomework.member.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private String version;
    private String title;
	
    @Bean
    public Docket apiV1() {
        version = "V1";
        title = "Member Auth API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bithumbhomework.member.controller.api.v1"))
                .paths(PathSelectors.ant("/v1/member/**"))
                .build()
                .apiInfo(apiInfo(title, version));

    }

    @Bean
    public Docket apiV2() {
        version = "V2";
        title = "Member Auth API " + version;

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName(version)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bithumbhomework.member.controller.api.v2"))
                .paths(PathSelectors.ant("/v2/member/**"))
                .build()
                .apiInfo(apiInfo(title, version));

    }	
    
	private ApiInfo apiInfo(String title, String versio) {
		return new ApiInfoBuilder().description("회원 가입, 로그인 및 조회 서비스를 위한 Back-End API").title(title)
				.version(version).build();
	}
    
//    private ApiInfo apiInfo(String title, String version) {
//        return new ApiInfo(
//                title,
//                "회원 가입, 로그인 및 조회 서비스를 위한 Back-End API",
//                version,
//                "www.bithumbhomework.com",
//                new Contact("Contact Me", "www.bithumbhomework.com", "beaman76@bithumbhomework.com"),
//                "Licenses",
//                "www.bithumbhomework.com",
//                new ArrayList<>());
//    }



}
