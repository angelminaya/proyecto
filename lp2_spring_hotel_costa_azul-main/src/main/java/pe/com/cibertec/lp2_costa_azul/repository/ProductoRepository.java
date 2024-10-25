package pe.com.cibertec.lp2_costa_azul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.cibertec.lp2_costa_azul.model.entity.ProductoEntity; // Cambiado el paquete

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {
}
