package gdghufs.jandies.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String TOKEN_KEY = "bearer-token";
    public static final String TOKEN_TAG = "Token";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement()
                    .addList(TOKEN_KEY))
            .components(new Components()
                    .addSecuritySchemes(TOKEN_KEY, this.createAPIKeyScheme()))
            .info(new Info()
                    .title("Jandies API Documentation")
                    .description("")
                    .version("")
                    .contact(new Contact()
                        .name("")
                        .email("")
                        .url(""))
                    .license(new License()
                        .name("")
                        .url("")))
            .servers(List.of(
                new Server().url("https://api.jandi.es").description("Production Server"),
                new Server().url("http://localhost:8080").description("Local Development")
            ));
    }

    @Bean
    public OpenApiCustomizer securityOpenApiCustomizer() {
        return o -> o.getPaths()
            .values()
            .forEach(item -> item.readOperations().forEach(operation -> {
                if (operation.getTags().remove(TOKEN_TAG)) {
                    operation.addSecurityItem(new SecurityRequirement().addList(TOKEN_KEY));
                }
            }));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .name("Authorization")
            .scheme("Bearer")
            .bearerFormat("JWT");
    }
}
