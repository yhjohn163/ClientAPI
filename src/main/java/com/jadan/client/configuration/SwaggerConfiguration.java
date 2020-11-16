package com.jadan.client.configuration;

import com.jadan.client.swagger.SwaggerPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;


@Configuration
@EnableSwagger2
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {

    @Bean
    public Docket clientAPI(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jadan.client"))
                .build()
                .directModelSubstitute(Pageable.class, SwaggerPage.class)
                .directModelSubstitute(LocalDate.class,String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Client REST API")
                .description("\"Client REST API for maintaining the clients information\"")
                .version("1.0.0")
                .contact(new Contact("Mohamoud Aljadan", "https://www.linkedin.com/in/mohamoud-aljadan-972644125", "mahmoudjadan@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .build();
    }


}
