package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.facade;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Usuario;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.UsuarioService;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthFacade {

    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthFacade(UsuarioService usuarioService,
                      BCryptPasswordEncoder passwordEncoder,
                      JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // LOGIN
    public ResponseEntity<?> login(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Complete todos los campos"));
        }

        var optUser = usuarioService.getUsuarioPorEmail(email);
        if (optUser.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Usuario o contraseña incorrectos"));
        }

        Usuario user = optUser.get();

        if (!Boolean.TRUE.equals(user.getActivo())) {
            return ResponseEntity.status(403).body(Map.of("message", "Cuenta inactiva"));
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail()); // Solo email
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "nombre", user.getNombre(),
                    "role", user.getRole()
            ));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Usuario o contraseña incorrectos"));
        }
    }

    // REGISTRO
    public ResponseEntity<?> register(String nombre, String email, String password) {
        if (nombre == null || nombre.isBlank() ||
                email == null || email.isBlank() ||
                password == null || password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Complete todos los campos"));
        }

        if (usuarioService.getUsuarioPorEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Correo ya registrado"));
        }

        Usuario user = usuarioService.registrarUsuario(nombre, email, password);

        String token = jwtUtil.generateToken(user.getEmail()); // Solo email

        return ResponseEntity.ok(Map.of(
                "message", "Usuario registrado correctamente",
                "token", token,
                "nombre", user.getNombre(),
                "role", user.getRole()
        ));
    }
}
