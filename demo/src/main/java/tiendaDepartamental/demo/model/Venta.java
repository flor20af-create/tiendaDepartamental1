package tiendaDepartamental.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal total;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles = new ArrayList<>();

    /**
     * Calcula el total de la venta sumando los subtotales
     * de cada detalle de venta.
     */
    public BigDecimal calcularTotal() {
        total = BigDecimal.ZERO;

        for (DetalleVenta detalle : detalles) {
            total = total.add(detalle.calcularSubtotal());
        }

        return total;
    }

    /**
     * Completa la venta calculando el total y actualizando
     * el stock de cada producto vendido.
     */
    public void completarVenta() {
        calcularTotal();

        for (DetalleVenta detalle : detalles) {
            detalle.getProducto().actualizarStock(detalle.getCantidad());
        }
    }

}
