package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente registrarCliente(Cliente cliente) throws Exception {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank() ||
                cliente.getEmail() == null || cliente.getEmail().isBlank() ||
                cliente.getPassword() == null || cliente.getPassword().isBlank()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        // Validación confirmación contraseña
        if (!cliente.getPassword().equals(cliente.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }

        // Validación email único
        if (repo.findByEmail(cliente.getEmail()).isPresent()) {
            throw new Exception("El email ya está registrado");
        }

        // Guardamos sin confirmPassword (es @Transient, no se guarda en DB)
        return repo.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente cliente) throws Exception {
        return repo.findById(id)
                .map(c -> {
                    c.setNombre(cliente.getNombre());
                    c.setEmail(cliente.getEmail());
                    c.setPassword(cliente.getPassword());
                    return repo.save(c);
                })
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
    }

    public void eliminarCliente(Long id) {
        repo.deleteById(id);
    }

    public java.util.List<Cliente> listarClientes() {
        return repo.findAll();
    }


}
