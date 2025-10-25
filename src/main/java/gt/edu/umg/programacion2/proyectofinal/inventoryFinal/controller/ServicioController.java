package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Servicio;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.ServicioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "http://localhost:3000")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.listarActivos();
    }

    @PostMapping
    public Servicio crearServicio(@RequestBody Servicio s) throws Exception {
        return servicioService.crearServicio(s);
    }

    @PutMapping("/{id}")
    public Servicio actualizarServicio(@PathVariable Long id, @RequestBody Servicio s) throws Exception {
        return servicioService.actualizarServicio(id, s);
    }

    @DeleteMapping("/{id}")
    public String eliminarServicio(@PathVariable Long id) throws Exception {
        servicioService.eliminarServicio(id);
        return "Servicio eliminado exitosamente";
    }
}
