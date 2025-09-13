
package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.modelo;

@Data
@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    private String mensaje;
    private String fecha;
    private boolean leida;

    // Relación: una notificación pertenece a un cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
