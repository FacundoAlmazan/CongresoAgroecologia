package model.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.ApplicationScoped;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import model.Usuario;

@ApplicationScoped
public class JwtService {

    private static final long HORAS_VALIDEZ = 8;

    private static final String SECRET_POR_DEFECTO =
            "jyaa-desarrollo-cambiar-esta-clave-secreta-por-una-de-32-bytes-o-mas";

    private final SecretKey key;

    public JwtService() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.length() < 32) {
            secret = SECRET_POR_DEFECTO;
        }

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generarToken(Usuario usuario) {
        Instant ahora = Instant.now();

        List<String> roles = usuario.getRoles()
                .stream()
                .map(rol -> rol.getNombre().name())
                .toList();

        return Jwts.builder()
                .subject(String.valueOf(usuario.getId()))
                .claim("dni", usuario.getNumeroIdentificacion())
                .claim("nombre", usuario.getNombre())
                .claim("roles", roles)
                .issuedAt(Date.from(ahora))
                .expiration(
                        Date.from(
                                ahora.plus(
                                        HORAS_VALIDEZ,
                                        ChronoUnit.HOURS
                                )
                        )
                )
                .signWith(key)
                .compact();
    }

    public Claims validarYObtenerClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
