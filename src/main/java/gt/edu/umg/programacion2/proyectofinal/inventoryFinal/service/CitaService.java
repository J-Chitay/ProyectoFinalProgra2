package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.service;

import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.dto.CitaDTO;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cita;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Cliente;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.model.Servicio;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.CitaRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ClienteRepository;
import gt.edu.umg.programacion2.proyectofinal.inventoryFinal.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;
    private final AuditoriaService auditoriaService; // <-- agregar

    public CitaService(CitaRepository citaRepository, ClienteRepository clienteRepository,
                       ServicioRepository servicioRepository, AuditoriaService auditoriaService) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.auditoriaService = auditoriaService;
    }

    public Cita agendarCita(CitaDTO dto, String usuario) throws Exception { // <-- usuario que realiza acción
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new Exception("Cliente no encontrado o inactivo"));

        Servicio servicio = servicioRepository.findById(dto.getServicioId())
                .orElseThrow(() -> new Exception("Servicio no encontrado o inactivo"));

        LocalDateTime ahora = LocalDateTime.now();
        if (dto.getFechaHora().isBefore(ahora.plusHours(2))) {
            throw new Exception("Las citas deben programarse con al menos 2 horas de antelación");
        }

        int hora = dto.getFechaHora().getHour();
        if (hora < 8 || hora >= 18) {
            throw new Exception("Horario fuera de atención. Elige una hora entre 08:00 y 18:00");
        }

        List<Cita> citaCliente = citaRepository.findByClienteIdAndEstado(cliente.getId(), "PENDIENTE");
        if (citaCliente.size() >= 3) {
            throw new Exception("El cliente ya tiene 3 citas pendientes. No puede agendar más.");
        }

        List<Cita> solapadas = citaRepository.findCitasSimultaneas(servicio.getId(), dto.getFechaHora());
        if (solapadas.size() >= servicio.getMaxConcurrentes()) {
            throw new Exception("El horario no está disponible. Por favor elige otra hora.");
        }

        Cita cita = new Cita();
        cita.setCliente(cliente);
        cita.setServicio(servicio);
        cita.setFechaHora(dto.getFechaHora());
        cita.setAccion(dto.getAccion());
        cita.setEstado("CONFIRMADA");

        Cita citaGuardada = citaRepository.save(cita);

        // Registrar en auditoría
        auditoriaService.registrarAccion(usuario, "CREAR CITA",
                "Cita ID " + citaGuardada.getId() + " para cliente " + cliente.getId() +
                        " y servicio " + servicio.getId() + " agendada para " + dto.getFechaHora());

        return citaGuardada;
    }
}

