package raizes_do_nordeste.config;

import java.nio.file.AccessDeniedException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

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
				"/unidades/**"
		).permitAll()
		
		.requestMatchers("/pedidos/**")
		.hasAnyRole("CLIENTE", "ATENDENTE", "GERENTE")
		
		.requestMatchers("/pagamentos/**")
		.hasAnyRole("CLIENTE", "ATENDENTE", "GERENTE")
		
		.requestMatchers("/fidelidade/**")
		.hasAnyRole("CLIENTE", "GERENTE")
		
		.requestMatchers("/estoque/**")
		.hasRole("GERENTE")
		
		.anyRequest().authenticated()
		)
		.exceptionHandling(exception -> exception
			.authenticationEntryPoint(
					(request,response,authException) -> {
						response.setStatus(401);
					}
				)
			.accessDeniedHandler(
					(request,response,AccessDeniedException) -> {
						response.setStatus(403);
					}
				)
			)
		.addFilterBefore(
				new JwtAuthenticationFilter(), 
				UsernamePasswordAuthenticationFilter.class
		);
				
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
