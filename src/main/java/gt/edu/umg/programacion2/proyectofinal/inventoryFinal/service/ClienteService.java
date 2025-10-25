package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto.ClienteDTO;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Cliente registrarCliente(ClienteDTO dto) {
        if (clienteRepository.findByDpi(dto.getDpi()).isPresent())
            throw new IllegalArgumentException("DPI ya registrado");

        if (clienteRepository.findByCorreo(dto.getCorreo()).isPresent())
            throw new IllegalArgumentException("Correo ya registrado");

        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setDpi(dto.getDpi());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        if (dto.getFechaNacimiento() != null)
            cliente.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));

        // Encriptar contraseña si viene
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente editarCliente(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        Optional<Cliente> dpiExist = clienteRepository.findByDpi(dto.getDpi());
        if (dpiExist.isPresent() && !dpiExist.get().getId().equals(id))
            throw new IllegalArgumentException("DPI ya registrado en otro cliente");

        Optional<Cliente> correoExist = clienteRepository.findByCorreo(dto.getCorreo());
        if (correoExist.isPresent() && !correoExist.get().getId().equals(id))
            throw new IllegalArgumentException("Correo ya registrado en otro cliente");

        cliente.setNombreCompleto(dto.getNombreCompleto());
        cliente.setDpi(dto.getDpi());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        if (dto.getFechaNacimiento() != null)
            cliente.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));

        // Actualizar contraseña si viene
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            cliente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return clienteRepository.save(cliente);
    }

    @Transactional
    public void desactivarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }
}
