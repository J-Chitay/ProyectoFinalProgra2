
package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.modelo;

@Data
@Entity
@Table(name = "citas")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    private String fecha;
    private String hora;
    private String estado;

    // Relación: una cita pertenece a un cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente") // Foreign Key en la tabla citas
    private Cliente cliente;

    // Relación: una cita corresponde a un servicio
    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    // Relación: una cita puede tener una factura
    @OneToOne(mappedBy = "cita")
    private Factura factura;
}
