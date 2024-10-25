package pe.com.cibertec.lp2_costa_azul.service;

import org.springframework.web.multipart.MultipartFile;

import pe.com.cibertec.lp2_costa_azul.model.entity.UsuarioEntity;

public interface UsuarioService {
    void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto);
    boolean validarUsuario(UsuarioEntity usuarioEntity);
    UsuarioEntity buscarUsuarioPorCorreo(String correo);
}
