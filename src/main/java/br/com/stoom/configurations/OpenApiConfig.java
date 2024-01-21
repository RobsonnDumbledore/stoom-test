package br.com.stoom.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

    private final static String DESCRIPTION = """
            <b> API created to manage products </b>
            """;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("StoomStore API")
                                .description(DESCRIPTION)
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("stoomstore@gmail.com")
                                                .email("stoomstore@gmail.com")
                                )
                );
    }

}
