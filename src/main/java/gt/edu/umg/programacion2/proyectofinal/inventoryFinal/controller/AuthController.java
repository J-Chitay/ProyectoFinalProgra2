package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Usuario;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.UsuarioRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String password = payload.get("password");

        Map<String, Object> response = new HashMap<>();
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);

        if (!optionalUsuario.isPresent()) {
            System.out.println("[DEBUG] Usuario no encontrado: " + email);
            response.put("status", "error");
            response.put("message", "Usuario o contraseña inválidos");
            return response;
        }

        Usuario usuario = optionalUsuario.get();
        System.out.println("[DEBUG] Usuario encontrado: " + usuario.getEmail());
        System.out.println("[DEBUG] Activo: " + usuario.getActivo());
        System.out.println("[DEBUG] Password en DB: " + usuario.getPassword());

        if (usuario.getActivo() == null || !usuario.getActivo()) {
            System.out.println("[DEBUG] Usuario inactivo");
            response.put("status", "error");
            response.put("message", "Usuario inactivo");
            return response;
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            System.out.println("[DEBUG] Contraseña incorrecta para: " + email);
            response.put("status", "error");
            response.put("message", "Usuario o contraseña inválidos");
            return response;
        }

        String token = jwtUtil.generateToken(usuario.getEmail());
        System.out.println("[DEBUG] Login exitoso para: " + email);

        response.put("status", "success");
        response.put("token", token);
        response.put("nombre", usuario.getNombre());
        response.put("role", usuario.getRole());
        return response;
    }

}
