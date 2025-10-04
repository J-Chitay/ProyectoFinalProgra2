package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.validation.ClienteValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    private final ClienteValidator validator;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
        this.validator = new ClienteValidator(repo);
    }

    public Cliente registrarCliente(Cliente cliente) throws Exception {
        // Validar usando el validator
        validator.validarCliente(cliente);

        // Guardar cliente en DB
        return repo.save(cliente);
    }


    public Cliente actualizarCliente(Long id, Cliente cliente) throws Exception {
        return repo.findById(id)
                .map(c -> {
                    c.setNombreCompleto(cliente.getNombreCompleto());
                    c.setIdentificador(cliente.getIdentificador());
                    c.setFechaNacimiento(cliente.getFechaNacimiento());
                    c.setTelefono(cliente.getTelefono());
                    c.setEmail(cliente.getEmail());
                    return repo.save(c);
                })
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
    }

    public void eliminarCliente(Long id) {
        repo.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return repo.findAll();
    }
}
