package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.audit.AuditListener;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Auditoria;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service // Spring lo mantiene como singleton por defecto
public class AuditoriaService {

    private final AuditoriaRepository repo;
    private final List<AuditListener> listeners = new ArrayList<>();

    public AuditoriaService(AuditoriaRepository repo) {
        this.repo = repo;
    }

    public void registerListener(AuditListener listener) {
        listeners.add(listener);
    }

    public void log(String usuario, String accion) {
        // persistir en BD
        Auditoria a = new Auditoria(usuario, accion);
        repo.save(a);

        // notificar listeners (observer)
        for (AuditListener l : listeners) {
            try {
                l.onAudit(usuario, accion);
            } catch (Exception ex) {
                // no dejar que un listener falle el flujo principal
                ex.printStackTrace();
            }
        }
    }
}
