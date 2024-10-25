package pe.com.cibertec.lp2_costa_azul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.cibertec.lp2_costa_azul.model.entity.UsuarioEntity; // Cambiado el paquete

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
    UsuarioEntity findByCorreo(String correo);
}
