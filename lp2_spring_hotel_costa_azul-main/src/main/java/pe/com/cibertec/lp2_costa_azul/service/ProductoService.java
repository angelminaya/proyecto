package pe.com.cibertec.lp2_costa_azul.service;

import pe.com.cibertec.lp2_costa_azul.model.entity.ProductoEntity;

import java.util.List;

public interface ProductoService {
    List<ProductoEntity> buscarTodosProductos();
    ProductoEntity buscarProductoPorId(Integer id);
}
