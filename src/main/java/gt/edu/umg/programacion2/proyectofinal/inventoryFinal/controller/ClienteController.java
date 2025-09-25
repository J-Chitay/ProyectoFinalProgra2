package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "http://localhost:3000") // permite solicitudes desde React
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.registrarCliente(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listarClientes();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(service.actualizarCliente(id, cliente));
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
