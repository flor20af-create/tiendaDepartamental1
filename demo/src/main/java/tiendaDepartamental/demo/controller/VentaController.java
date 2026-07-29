package tiendaDepartamental.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tiendaDepartamental.demo.model.Venta;
import tiendaDepartamental.demo.repository.VentaRepository;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    // Obtener todas las ventas
    @GetMapping
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    // Obtener una venta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerVenta(@PathVariable Long id) {
        return ventaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registrar una nueva venta
    @PostMapping
    public Venta guardarVenta(@RequestBody Venta venta) {

        // Calcula el total y actualiza el stock
        venta.completarVenta();

        return ventaRepository.save(venta);
    }

    // Actualizar una venta
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(
            @PathVariable Long id,
            @RequestBody Venta ventaActualizada) {

        return ventaRepository.findById(id)
                .map(venta -> {

                    venta.setFecha(ventaActualizada.getFecha());
                    venta.setCliente(ventaActualizada.getCliente());
                    venta.setDetalles(ventaActualizada.getDetalles());

                    // Recalcula el total
                    venta.completarVenta();

                    return ResponseEntity.ok(ventaRepository.save(venta));

                }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {

        return ventaRepository.findById(id)
                .map(venta -> {
                    ventaRepository.delete(venta);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
