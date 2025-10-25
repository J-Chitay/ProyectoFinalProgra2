package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Auditoria;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    // Método principal: registra cualquier acción con descripción
    public void registrarAccion(String usuario, String accion, String descripcion) {
        Auditoria a = new Auditoria();
        a.setUsuario(usuario);
        a.setAccion(accion + (descripcion != null && !descripcion.isBlank() ? ": " + descripcion : ""));
        a.setFecha(LocalDateTime.now());
        auditoriaRepository.save(a);
    }

    // Método corto para usar solo usuario + acción
    public void log(String usuario, String accion) {
        registrarAccion(usuario, accion, "");
    }

    // Devuelve todas las entradas de auditoría
    public List<Auditoria> obtenerTodas() {
        return auditoriaRepository.findAll();
    }
}
