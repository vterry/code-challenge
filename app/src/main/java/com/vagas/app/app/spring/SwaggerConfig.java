package com.vagas.app.app.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vagas.app.app.resource"))
                .paths(PathSelectors.ant("/v1/*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("VAGAS.com APP")
                .description("\"Pequena aplicação que fornece APIS Rest que permitem realizar o cadastro de " +
                        "novas vagas, candidatos, candidaturas e rankeamento.\"")
                .version("0.0.1")
                .license("VAGAS.com Challenge License")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .contact(new Contact("Victor Felipe", "", "victor.fsouza2@gmail.com"))
                .build();
    }

}
