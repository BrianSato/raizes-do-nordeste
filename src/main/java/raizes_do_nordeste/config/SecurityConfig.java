package raizes_do_nordeste.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import raizes_do_nordeste.api.exception.ErroResponse;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	private final ObjectMapper objectMapper =
	        new ObjectMapper().findAndRegisterModules();
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
		.requestMatchers(
				"/auth",
				"/auth/**",
				"/produtos",
				"/produtos/**",
				"/unidades/**",
				"/error",
				"/v3/api-docs/**",
				"/swagger-ui/**",
				"/swagger-ui.html"
		).permitAll()
		
		.requestMatchers("/pedidos","/pedidos/**")
		.hasAnyRole("CLIENTE", "ATENDENTE", "GERENTE")
		
		.requestMatchers("/pagamentos/**")
		.hasAnyRole("CLIENTE", "ATENDENTE", "GERENTE")
		
		.requestMatchers(HttpMethod.GET,"/fidelidade/**")
		.hasAnyRole("CLIENTE", "GERENTE")
		
		.requestMatchers(HttpMethod.POST,"/fidelidade")
		.hasRole("GERENTE")
				
		.requestMatchers(HttpMethod.POST,"/fidelidade/{id}/pontos")
		.hasRole("GERENTE")
		
		.requestMatchers(HttpMethod.POST,"/fidelidade/{id}/pontos/utilizar")
		.hasAnyRole("CLIENTE","GERENTE")
		
		.requestMatchers("/estoque/**")
		.hasRole("GERENTE")
		
		.anyRequest().authenticated()
		)
		
		.exceptionHandling(exception -> exception
		    .authenticationEntryPoint((request, response, authException) -> {
		        response.setStatus(401);
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		
		        ErroResponse erro = new ErroResponse(
		            LocalDateTime.now(),
		            401,
		            "NAO_AUTENTICADO",
		            "É necessário autenticar para acessar este recurso."
		        );
		
		        objectMapper.writeValue(response.getOutputStream(), erro);
		    })
		    .accessDeniedHandler((request, response, accessDeniedException) -> {
		        response.setStatus(403);
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");
		
		        ErroResponse erro = new ErroResponse(
		            LocalDateTime.now(),
		            403,
		            "SEM_PERMISSAO",
		            "Você não tem permissão para acessar este recurso."
		        );
		
		        objectMapper.writeValue(response.getOutputStream(), erro);
		    })
		)
		.addFilterBefore(
		        jwtAuthenticationFilter,
		        UsernamePasswordAuthenticationFilter.class
		);
				
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
