package raizes_do_nordeste.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public static final String SECRET_KEY =
			"raizes-do-nordeste-chave-secreta-para-jwt-2026";
	private static final Key key = Keys.hmacShaKeyFor(
			SECRET_KEY.getBytes(StandardCharsets.UTF_8)
			);
	
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
