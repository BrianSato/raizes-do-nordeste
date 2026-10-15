
package raizes_do_nordeste;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class JwtExpirationTest {

    @Test
    void deveRejeitarTokenExpirado() {

        String segredoDeTeste = "chave-de-teste-com-32-bytes-minimo!";

        SecretKey chave = Keys.hmacShaKeyFor(
                segredoDeTeste.getBytes(StandardCharsets.UTF_8)
        );

        Date emitidoEm = new Date(System.currentTimeMillis() - 120_000);
        Date expiradoEm = new Date(System.currentTimeMillis() - 60_000);

        String token = Jwts.builder()
                .subject("usuario@exemplo.com")
                .issuedAt(emitidoEm)
                .expiration(expiradoEm)
                .signWith(chave)
                .compact();

        assertThrows(
                ExpiredJwtException.class,
                () -> Jwts.parser()
                        .verifyWith(chave)
                        .build()
                        .parseSignedClaims(token)
        );
    }
}
