package raizes_do_nordeste.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;

	private Key key;

	@PostConstruct
	public void inicializarChave() {
	    this.key = Keys.hmacShaKeyFor(
	            secret.getBytes(StandardCharsets.UTF_8)
	    );
	}
	
	public String gerarToken(Integer usuarioId, String email, String role) {
		
		return Jwts.builder()
				.subject(email)
				.claim("id", usuarioId)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(key)
				.compact();
	}
}
