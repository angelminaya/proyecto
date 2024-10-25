package pe.com.cibertec.lp2_costa_azul.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.cibertec.lp2_costa_azul.model.entity.ProductoEntity;
import pe.com.cibertec.lp2_costa_azul.repository.ProductoRepository;
import pe.com.cibertec.lp2_costa_azul.service.ProductoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<ProductoEntity> buscarTodosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public ProductoEntity buscarProductoPorId(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }
}
