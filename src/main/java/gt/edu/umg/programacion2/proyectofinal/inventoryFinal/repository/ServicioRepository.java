package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Optional<Servicio> findByCodigo(String codigo);
    List<Servicio> findByActivoTrue();
}
