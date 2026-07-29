package tiendaDepartamental.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProducto;

    @Column(nullable = false)
    private BigDecimal precio;

    private String categoria;

    private int stock;

    /**
     * Verifica si el producto está disponible.
     * @return true si hay existencias, false en caso contrario.
     */
    public boolean verificarDisponibilidad() {
        return stock > 0;
    }

    /**
     * Muestra la información del producto.
     */
    public void mostrarDetalle() {
        System.out.println("Producto: " + nombreProducto);
        System.out.println("Precio: $" + precio);
        System.out.println("Categoría: " + categoria);
        System.out.println("Stock: " + stock);
    }

    /**
     * Actualiza el stock restando la cantidad vendida.
     * @param cantidadProducto cantidad a descontar del inventario.
     */
    public void actualizarStock(int cantidadProducto) {
        if (cantidadProducto > 0 && cantidadProducto <= stock) {
            stock -= cantidadProducto;
        } else {
            throw new IllegalArgumentException("Cantidad inválida o stock insuficiente.");
        }
    }
}