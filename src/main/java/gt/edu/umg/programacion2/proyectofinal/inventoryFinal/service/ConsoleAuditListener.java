package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

public class ConsoleAuditListener implements AuditoriaListener {

    @Override
    public void onAction(String accion, String usuario) {
        System.out.println("Auditoría: " + accion + " | Usuario: " + usuario);
    }
}
