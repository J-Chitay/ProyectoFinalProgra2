package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.audit.AuditListener;
import org.springframework.stereotype.Component;

@Component
public class ConsoleAuditListener implements AuditListener {

    public ConsoleAuditListener(AuditoriaService auditoriaService) {
        // registrarse automáticamente al arrancar
        auditoriaService.registerListener(this);
    }

    @Override
    public void onAudit(String usuario, String accion) {
        // ejemplo: log por consola; aquí podrías enviar email, webhook, etc.
        System.out.println("[AUDIT LISTENER] " + usuario + " -> " + accion);
    }
}
