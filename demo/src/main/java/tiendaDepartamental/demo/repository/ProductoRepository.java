package tiendaDepartamental.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tiendaDepartamental.demo.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

}
