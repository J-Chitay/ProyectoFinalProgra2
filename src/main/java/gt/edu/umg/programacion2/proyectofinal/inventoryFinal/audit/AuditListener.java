package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.audit;

public interface AuditListener {
    void onAudit(String usuario, String accion);
}
