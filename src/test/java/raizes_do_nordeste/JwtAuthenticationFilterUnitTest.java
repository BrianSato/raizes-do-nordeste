
package raizes_do_nordeste;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import raizes_do_nordeste.config.JwtAuthenticationFilter;

class JwtAuthenticationFilterUnitTest {

    private static class FiltroTestavel extends JwtAuthenticationFilter {
        void executar(HttpServletRequest request,
                      HttpServletResponse response,
                      FilterChain filterChain)
                throws ServletException, IOException {
            super.doFilterInternal(request, response, filterChain);
        }
    }

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void deveRejeitarTokenExpiradoEContinuarFiltro() throws Exception {

        String segredo = "chave-de-teste-com-32-bytes-minimo!";

        SecretKey chave = Keys.hmacShaKeyFor(
                segredo.getBytes(StandardCharsets.UTF_8)
        );

        Date emitidoEm = new Date(System.currentTimeMillis() - 120_000);
        Date expiradoEm = new Date(System.currentTimeMillis() - 60_000);

        String tokenExpirado = Jwts.builder()
                .subject("usuario@exemplo.com")
                .claim("role", "CLIENTE")
                .issuedAt(emitidoEm)
                .expiration(expiradoEm)
                .signWith(chave)
                .compact();

        FiltroTestavel filtro = new FiltroTestavel();
        ReflectionTestUtils.setField(filtro, "key", chave);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + tokenExpirado);

        filtro.executar(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }
}
