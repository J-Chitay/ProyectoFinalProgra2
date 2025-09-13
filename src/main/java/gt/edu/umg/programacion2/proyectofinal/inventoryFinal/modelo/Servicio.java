

package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.modelo;

@Data
@Entity
@Table(name = "servicios")
public class Servicio {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    private String nombreServicio;
    private String descripcion;
    private double precio;
    private int duracion;
    private int maxConcurrentes;

    // Relación: un servicio puede estar en muchas citas
    @OneToMany(mappedBy = "servicio")
    private List<Cita> citas;
}
