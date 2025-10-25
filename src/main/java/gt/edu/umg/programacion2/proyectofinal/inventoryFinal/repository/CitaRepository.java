package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("SELECT c FROM Cita c WHERE c.cliente.id = :clienteId AND c.estado = :estado")
    List<Cita> findByClienteIdAndEstado(Long clienteId, String estado);

    @Query("SELECT c FROM Cita c WHERE c.servicio.id = :servicioId AND c.fechaHora = :fecha")
    List<Cita> findCitasSimultaneas(Long servicioId, LocalDateTime fecha);
}
