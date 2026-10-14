package raizes_do_nordeste.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Value("${jwt.secret}")
	private String secret;

	private Key key;

	@PostConstruct
	public void inicializarChave() {
	    this.key = Keys.hmacShaKeyFor(
	            secret.getBytes(StandardCharsets.UTF_8)
	    );
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		
		String authorizationHeader = 
				request.getHeader("Authorization");
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authorizationHeader.substring(7);
		
		try {
			
			Claims claims = Jwts.parser()
					.verifyWith((javax.crypto.SecretKey) key)
					.build()
					.parseSignedClaims(token)
					.getPayload();
			
			String email = claims.getSubject();
			String role = claims.get("role", String.class);
			
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(
							email, 
							null,
							java.util.List.of(
									new SimpleGrantedAuthority("ROLE_" + role
									)
							)
					);
			SecurityContextHolder
			.getContext()
			.setAuthentication(authentication);		
			
		}catch (Exception exception) {

		    SecurityContextHolder.clearContext();
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
