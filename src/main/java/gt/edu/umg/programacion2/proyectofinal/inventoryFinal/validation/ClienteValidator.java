package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.validation;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;

import java.util.Optional;
import java.util.regex.Pattern;


public class ClienteValidator {
    private final ClienteRepository repo;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    public ClienteValidator(ClienteRepository repo) {
        this.repo = repo;
    }

    public Cliente registrarCliente(Cliente cliente, String confirmPassword) throws Exception {
        if (cliente.getNombre().isEmpty() || cliente.getEmail().isEmpty() || cliente.getPassword().isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new Exception("Email inválido");
        }

        if (!cliente.getPassword().equals(cliente.getConfirmPassword())) {
            throw new Exception("Las contraseñas no coinciden");
        }

        Optional<Cliente> existing = repo.findByEmail(cliente.getEmail());
        if (existing.isPresent()) {
            throw new Exception("Email ya registrado");
        }

        Cliente agregado = repo.save(cliente);
        if (agregado == null) {
            throw new Exception("No se pudo registrar el cliente");
        }
        repo.save(cliente);
        return cliente;
    }
}
