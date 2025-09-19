package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import org.springframework.stereotype.Service;
import java.util.Optional;


import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente registrar(Cliente c) {
        return repo.save(c);
    }

    public List<Cliente> listarTodos() {
        return repo.findAll();
    }

    // Buscar por ID
    public Optional<Cliente> buscarCliente(Long id) {
        return repo.findById(id);
    }

    // Actualizar cliente
    public Optional<Cliente> actualizarCliente(Long id, Cliente clienteActualizado) {
        return repo.findById(id).map(cliente -> {
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setEmail(clienteActualizado.getEmail());
            return repo.save(cliente);
        });
    }

    public boolean eliminar(Long id) {
        if (repo.findById(id).isPresent()) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }


}
