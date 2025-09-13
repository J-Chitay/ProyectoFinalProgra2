
package gt.edu.umg.programacion2.proyectofinal.inventoryFinal.modelo;

@Data
@Entity
@Table(name = "facturas")

public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    private String fecha;
    private double total;

    // Relación: una factura pertenece a un cliente
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    // Relación: una factura está ligada a una cita
    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;
}
