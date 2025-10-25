package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto;

import java.time.LocalDateTime;

public class CitaDTO {
    private Long clienteId;
    private Long servicioId;
    private LocalDateTime fechaHora;
    private String accion; // notas opcionales

    // getters y setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
}
