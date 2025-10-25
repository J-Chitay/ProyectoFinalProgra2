package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDpi(String dpi);
    Optional<Cliente> findByCorreo(String correo);
}
