package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.controller;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Auditoria;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service.AuditoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/auditoria")
    public List<Auditoria> listarAuditoria() {
        return auditoriaService.obtenerTodas();
    }
}
