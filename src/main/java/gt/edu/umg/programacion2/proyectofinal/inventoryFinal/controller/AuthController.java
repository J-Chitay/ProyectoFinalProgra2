package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Usuario;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.facade.AuthFacade;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.UsuarioRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UsuarioRepository usuarioRepo;
    private final AuthFacade authFacade;
    private final AuditoriaService auditoriaService;

    public AuthController(UsuarioRepository usuarioRepo,
                          AuthFacade authFacade,
                          AuditoriaService auditoriaService) {
        this.usuarioRepo = usuarioRepo;
        this.authFacade = authFacade;
        this.auditoriaService = auditoriaService;
    }

    // DTO simple para login (opcional crear clase LoginRequest)
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if (req.email == null || req.password == null || req.email.isBlank() || req.password.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Por favor complete todos los campos correctamente."));
        }

        Optional<String> maybeToken = authFacade.login(req.email, req.password);
        if (maybeToken.isPresent()) {
            String token = maybeToken.get();
            String nombre = usuarioRepo.findByEmail(req.email).map(Usuario::getNombre).orElse("Usuario");

            // registrar auditoría
            auditoriaService.log(nombre, "Login exitoso");

            return ResponseEntity.ok(Map.of("token", token, "nombre", nombre));
        } else {
            // registrar intento fallido (opcional)
            usuarioRepo.findByEmail(req.email).ifPresent(u -> auditoriaService.log(u.getNombre(), "Login fallido"));
            return ResponseEntity.status(401).body(Map.of("message", "Usuario o contraseña incorrectos"));
        }
    }
}
