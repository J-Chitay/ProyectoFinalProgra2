package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Servicio;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository repo;

    public ServicioService(ServicioRepository repo) {
        this.repo = repo;
    }

    // Listar servicios activos
    public List<Servicio> listarActivos() {
        return repo.findByActivoTrue();
    }

    // Crear nuevo servicio
    public Servicio crearServicio(Servicio s) throws Exception {
        if (s.getCodigo() == null || s.getCodigo().isBlank() ||
                s.getNombre() == null || s.getNombre().isBlank()) {
            throw new Exception("Código y nombre son obligatorios");
        }

        Optional<Servicio> existente = repo.findByCodigo(s.getCodigo());
        if (existente.isPresent()) {
            throw new Exception("El código ya está en uso. Elija otro.");
        }

        if (s.getPrecio() == null || s.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El precio debe ser mayor que cero");
        }

        if (s.getDuracion() == null || s.getDuracion() <= 0) {
            throw new Exception("La duración debe ser mayor que cero");
        }

        if (s.getMaxConcurrentes() == null || s.getMaxConcurrentes() < 1 || s.getMaxConcurrentes() > 10) {
            throw new Exception("Cupo inválido. Debe estar entre 1 y 10");
        }

        s.setActivo(true);
        return repo.save(s);
    }

    // Actualizar servicio existente
    public Servicio actualizarServicio(Long id, Servicio s) throws Exception {
        Servicio existente = repo.findById(id)
                .orElseThrow(() -> new Exception("Servicio no encontrado"));

        if (s.getPrecio() == null || s.getPrecio().compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("El precio debe ser mayor que cero");

        if (s.getDuracion() == null || s.getDuracion() <= 0)
            throw new Exception("Duración inválida");

        if (s.getMaxConcurrentes() == null || s.getMaxConcurrentes() < 1 || s.getMaxConcurrentes() > 10)
            throw new Exception("Cupo inválido. Debe estar entre 1 y 10");

        existente.setNombre(s.getNombre());
        existente.setDescripcion(s.getDescripcion());
        existente.setPrecio(s.getPrecio());
        existente.setDuracion(s.getDuracion());
        existente.setMaxConcurrentes(s.getMaxConcurrentes());

        return repo.save(existente);
    }

    // Eliminación lógica con verificación
    public void eliminarServicio(Long id) throws Exception {
        Servicio existente = repo.findById(id)
                .orElseThrow(() -> new Exception("Servicio no encontrado"));

        // TODO: verificar si tiene citas futuras (puedo ayudarte a agregarlo)
        existente.setActivo(false);
        repo.save(existente);
    }
}
