import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.modelo;

@Data // Lombok: genera getters, setters, toString, equals, hashCode automáticamente
@Entity // Marca la clase como entidad JPA → crea tabla en la BD
@Table(name = "clientes") // Nombre de la tabla en la BD

public class Cliente {
    @Id // Marca la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long idCliente;

    private String nombreCompleto;
    private String fechaNacimiento;
    private String telefono;
    private String email;
    
    // Relación: un cliente puede tener varias citas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Cita> citas;

    // Relación: un cliente puede tener varias facturas
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Factura> facturas;

    // Relación: un cliente puede tener varias notificaciones
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Notificacion> notificaciones;
}
