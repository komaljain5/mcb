package com.nagaro.sms.config;

	import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.annotations.ApiOperation;
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
	/**
	 * This class can be used to configure the interceptor for the UMAS application.
	 * 
	 *@author komaljain01
	 *
	 */
	@Configuration
	@EnableSwagger2
	public class SwaggerConfig implements WebMvcConfigurer  {
		
		
		@Bean
	   	public Docket api() {
	   		return new Docket(DocumentationType.SWAGGER_2)
	   				.securityContexts(Arrays.asList(securityContext()))
	                .securitySchemes(Arrays.asList(apiKey()))
	                .select()
	                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
	   				.paths(PathSelectors.any()).build()
	   				.apiInfo(apiInfo());

	   	}

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Student Management System").build();
	    }
	    
	    private ApiKey apiKey() { 
	        return new ApiKey("JWT", "Authorization", "header"); 
	    }
	    
	    private SecurityContext securityContext() {
	        return SecurityContext.builder().securityReferences(defaultAuth()).build();
	    }

	    private List<SecurityReference> defaultAuth() {
	        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	        authorizationScopes[0] = authorizationScope;
	        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	    }

		
	   	@Override
	   	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	   		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
	   		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	   	}
	}

