package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Usuario;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepo, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registrarUsuario(String nombre, String email, String password) {
        if (usuarioRepo.findByEmail(email).isPresent())
            throw new IllegalArgumentException("Correo ya registrado");

        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // encriptada
        user.setActivo(true);
        user.setRole("USER"); // rol por defecto, puedes cambiarlo

        return usuarioRepo.save(user);
    }

    public Optional<Usuario> getUsuarioPorEmail(String email) {
        return usuarioRepo.findByEmail(email);
    }
}
