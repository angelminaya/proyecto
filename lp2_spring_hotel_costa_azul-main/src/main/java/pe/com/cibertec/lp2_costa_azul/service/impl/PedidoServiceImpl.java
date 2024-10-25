package pe.com.cibertec.lp2_costa_azul.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.com.cibertec.lp2_costa_azul.model.entity.PedidoEntity;
import pe.com.cibertec.lp2_costa_azul.repository.PedidoRepository;
import pe.com.cibertec.lp2_costa_azul.service.PedidoService;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    
    @Override
    public void crearPedido(PedidoEntity pedidoEntity) {
        // Guardar el pedido en el repositorio
        pedidoRepository.save(pedidoEntity);
    }
}
