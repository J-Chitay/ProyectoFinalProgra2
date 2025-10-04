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

    public void validarCliente(Cliente cliente) throws Exception {
        if (cliente.getNombreCompleto() == null || cliente.getNombreCompleto().isBlank() ||
                cliente.getIdentificador() == null || cliente.getIdentificador().isBlank() ||
                cliente.getFechaNacimiento() == null ||
                cliente.getTelefono() == null ||
                cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new Exception("Formato de email inválido");
        }

        Optional<Cliente> existingEmail = repo.findByEmail(cliente.getEmail());
        if (existingEmail.isPresent()) {
            throw new Exception("Email ya registrado");
        }

        Optional<Cliente> existingId = repo.findByIdentificador(cliente.getIdentificador());
        if (existingId.isPresent()) {
            throw new Exception("Identificador ya registrado");
        }
    }
}
