
package raizes_do_nordeste;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${jwt.secret}")
    private String secret;

    @Test
    void deveRetornar401QuandoTokenEstiverExpirado() throws Exception {

        SecretKey key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        Date emitidoEm = new Date(System.currentTimeMillis() - 120_000);
        Date expiradoEm = new Date(System.currentTimeMillis() - 60_000);

        String tokenExpirado = Jwts.builder()
                .subject("teste@exemplo.com")
                .claim("role", "CLIENTE")
                .issuedAt(emitidoEm)
                .expiration(expiradoEm)
                .signWith(key)
                .compact();

        mockMvc.perform(get("/pedidos")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenExpirado))
                .andExpect(status().isUnauthorized());
    }
}
