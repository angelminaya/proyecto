package pe.com.cibertec.lp2_costa_azul.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.lp2_costa_azul.model.entity.DetalleReservaEntity;
import pe.com.cibertec.lp2_costa_azul.model.entity.Reserva;
import pe.com.cibertec.lp2_costa_azul.model.entity.ReservaEntity;
import pe.com.cibertec.lp2_costa_azul.model.entity.HabitacionEntity;
import pe.com.cibertec.lp2_costa_azul.model.entity.ClienteEntity;
import pe.com.cibertec.lp2_costa_azul.service.ReservaService;

@Controller
@RequiredArgsConstructor
public class ReservaController {
	
	private final ReservaService reservaService;
	
	@GetMapping("/guardar_factura")
	public String guardarFactura(HttpSession session) {
		String correoCliente = session.getAttribute("usuario").toString();
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setCorreo(correoCliente);
		
		// Setear parte de la reserva
		ReservaEntity reservaEntity = new ReservaEntity();
		reservaEntity.setFechaReserva(LocalDate.now()); // Obtener fecha actual
		reservaEntity.setClienteEntity(clienteEntity);
		
		// Formar el detalle de la reserva
		List<DetalleReservaEntity> detalleReservaEntityList = new ArrayList<>();
		
		List<Reserva> reservaSesion = null;
		if (session.getAttribute("carrito") == null) {
			reservaSesion = new ArrayList<>();
		} else {
			reservaSesion = (List<Reserva>) session.getAttribute("carrito");
		}
		// Cargar detalle de la reserva entity
		for (Reserva res : reservaSesion) {
			DetalleReservaEntity detalleReservaEntity = new DetalleReservaEntity();
			HabitacionEntity habitacionEntity = new HabitacionEntity();
			habitacionEntity.setHabitacionId(res.getProductoId()); // Aquí asumiendo que el ID de la habitación se guarda como productoId
			
			detalleReservaEntity.setHabitacionEntity(habitacionEntity);
			detalleReservaEntity.setCantidad(res.getCantidad());
			detalleReservaEntity.setReservaEntity(reservaEntity);
			detalleReservaEntityList.add(detalleReservaEntity);
		}
		
		reservaEntity.setDetalleReserva(detalleReservaEntityList);
		reservaService.crearReserva(reservaEntity);
		session.removeAttribute("carrito");
		return "redirect:/menu";
	}
}
