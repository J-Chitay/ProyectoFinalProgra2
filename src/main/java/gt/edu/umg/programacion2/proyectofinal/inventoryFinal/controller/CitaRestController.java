package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto.CitaDTO;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cita;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.CitaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:3000")
public class CitaRestController {

    private final CitaService citaService;

    public CitaRestController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping("/agendar")
    public Cita agendarCita(@RequestBody CitaDTO dto, @RequestParam String usuario) throws Exception {
        return citaService.agendarCita(dto, usuario);
    }
}

