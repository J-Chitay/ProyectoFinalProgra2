package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.facade;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Usuario;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.UsuarioRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class AuthFacade {

    private final UsuarioRepository usuarioRepo;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private Key key;

    public AuthFacade(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @PostConstruct
    private void init() {
        // crea la Key a partir del secreto del properties
        byte[] secretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(secretBytes);
    }

    /**
     * Intenta login; si OK devuelve token JWT
     */
    public Optional<String> login(String email, String password) {
        Optional<Usuario> u = usuarioRepo.findByEmail(email);
        if (u.isPresent() && u.get().getPassword().equals(password)) {
            Date now = new Date();
            Date exp = new Date(now.getTime() + expirationMs);

            String jwt = Jwts.builder()
                    .setSubject(u.get().getEmail())
                    .claim("nombre", u.get().getNombre())
                    .claim("role", u.get().getRole())
                    .setIssuedAt(now)
                    .setExpiration(exp)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            return Optional.of(jwt);
        }
        return Optional.empty();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException ex) {
            return false;
        }
    }

    public Optional<String> getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return Optional.ofNullable(claims.getSubject());
        } catch (JwtException ex) {
            return Optional.empty();
        }
    }
}
