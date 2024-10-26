package pe.com.cibertec.lp2_costa_azul.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.lp2_costa_azul.model.entity.UsuarioEntity;
import pe.com.cibertec.lp2_costa_azul.repository.UsuarioRepository;
import pe.com.cibertec.lp2_costa_azul.service.UsuarioService;
import pe.com.cibertec.lp2_costa_azul.utils.Utilitarios;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void crearUsuario(UsuarioEntity usuarioEntity, MultipartFile foto) {
        // 1. Guardar la foto
        String nombreFoto = Utilitarios.guardarImagen(foto);
        usuarioEntity.setUrlImagen(nombreFoto);
        
        // 2. Extraer el hash del password
        String passwordHash = Utilitarios.extraerHash(usuarioEntity.getPassword());
        usuarioEntity.setPassword(passwordHash);
        
        // 3. Guardar en la base de datos
        usuarioRepository.save(usuarioEntity);
    }

    @Override
    public boolean validarUsuario(UsuarioEntity usuarioFormulario) {
        // 1. Buscar correo en base de datos
        UsuarioEntity usuarioEncontrado = usuarioRepository
                .findByCorreo(usuarioFormulario.getCorreo());
        
        // 2. Correo existe
        if (usuarioEncontrado == null) {
            return false;
        }
        
        // 3. Validar si el password del formulario hace match con el hash de la base de datos
        return Utilitarios.checkPassword(usuarioFormulario.getPassword(),
                usuarioEncontrado.getPassword());
    }

    @Override
    public UsuarioEntity buscarUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}
