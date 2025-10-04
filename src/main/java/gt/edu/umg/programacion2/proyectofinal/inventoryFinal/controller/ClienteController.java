package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:3000")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = service.registrarCliente(cliente);
            return ResponseEntity.ok(Map.of(
                    "message", "Cliente registrado con ID " + nuevoCliente.getId(),
                    "id", nuevoCliente.getId()
            ));
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg.contains("obligatorios")) {
                return ResponseEntity.badRequest().body(Map.of("message", "Debe completar todos los campos obligatorios"));
            } else if (msg.contains("Email ya registrado")) {
                return ResponseEntity.status(409).body(Map.of("message", "El email ya está registrado"));
            } else if (msg.contains("Identificador ya registrado")) {
                return ResponseEntity.status(409).body(Map.of("message", "El identificador ya está registrado"));
            } else if (msg.contains("Formato de email inválido")) {
                return ResponseEntity.badRequest().body(Map.of("message", "Formato de email inválido"));
            } else {
                return ResponseEntity.status(500).body(Map.of("message", "Error al registrar, por favor inténtelo más tarde"));
            }
        }
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente actualizado = service.actualizarCliente(id, cliente);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminarCliente(id);
        return ResponseEntity.ok("Cliente eliminado");
    }
}
