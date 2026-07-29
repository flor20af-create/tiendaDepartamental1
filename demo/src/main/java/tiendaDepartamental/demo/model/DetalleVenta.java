package tiendaDepartamental.demo.model;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;

    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    /**
     * Calcula el subtotal del detalle de venta.
     * subtotal = precio del producto × cantidad
     */
    public BigDecimal calcularSubtotal() {
        if (producto == null) {
            return BigDecimal.ZERO;
        }

        subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        return subtotal;
    }
}
