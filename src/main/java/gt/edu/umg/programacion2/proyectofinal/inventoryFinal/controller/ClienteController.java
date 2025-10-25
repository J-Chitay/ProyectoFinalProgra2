package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto.ClienteDTO;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @PostMapping
    public ResponseEntity<?> registrarCliente(@RequestBody ClienteDTO dto) {
        try {
            Cliente cliente = clienteService.registrarCliente(dto);
            return ResponseEntity.ok("Cliente registrado con ID " + cliente.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al registrar. Por favor inténtelo más tarde.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
        try {
            clienteService.editarCliente(id, dto);
            return ResponseEntity.ok("Datos de cliente actualizados exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al actualizar datos. Intente nuevamente más tarde.");
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarCliente(@PathVariable Long id) {
        try {
            clienteService.desactivarCliente(id);
            return ResponseEntity.ok("Cliente desactivado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al desactivar cliente.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar cliente.");
        }
    }
}
