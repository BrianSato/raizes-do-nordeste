package raizes_do_nordeste.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		
		return new OpenAPI()
				.info(new Info()
						.title("Raizes do Nordeste API")
						.version("1.0")
						.description(
								"API backend para gerenciamento de pedidos, "
								+ "pagamentos, produtos, estoque e fidelidade."
						))
				.components(new Components()
						.addSecuritySchemes(
								"bearerAuth",
								new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
						));
	}
}
