package de.ait_tr.g_36_shop.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Cohort 36 Shop",
                description = "Application for testing REST API Internet Shop on Spring Boot",
                version = "1.0.0",
                contact = @Contact(
                        name = "Leonid",
                        email = "k_l_b_1963@hotmail.com",
                        url = "http://ait-tr.de"
                )
        )
)
public class SwaggerConfig {

}
