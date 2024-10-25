package pe.com.cibertec.lp2_costa_azul.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.com.cibertec.lp2_costa_azul.model.entity.DetalleReservaEntity;
import pe.com.cibertec.lp2_costa_azul.model.entity.Reserva;
import pe.com.cibertec.lp2_costa_azul.model.entity.HabitacionEntity;
import pe.com.cibertec.lp2_costa_azul.model.entity.ClienteEntity;
import pe.com.cibertec.lp2_costa_azul.service.HabitacionService;
import pe.com.cibertec.lp2_costa_azul.service.ClienteService;
import pe.com.cibertec.lp2_costa_azul.service.impl.PdfService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;
    private final ClienteService clienteService;
    private final PdfService pdfService;

    @GetMapping("/menu")
    public String mostrarMenu(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/";
        }

        String correoSesion = session.getAttribute("usuario").toString();
        ClienteEntity clienteEncontrado = clienteService.buscarUsuarioPorCorreo(correoSesion);
        model.addAttribute("foto", clienteEncontrado.getUrlImagen());

        List<HabitacionEntity> listaHabitaciones = habitacionService.buscarTodasHabitaciones();
        model.addAttribute("habitaciones", listaHabitaciones);
        
        // cantidad reservas
        List<Reserva> reservaSesion = null;
        if (session.getAttribute("carrito") == null) {
            reservaSesion = new ArrayList<>();
        } else {
            reservaSesion = (List<Reserva>) session.getAttribute("carrito");
        }
        model.addAttribute("cant_carrito", reservaSesion.size());
        
        // ver carrito con datos
        List<DetalleReservaEntity> detalleReservaEntity = new ArrayList<>();
        Double totalReserva = 0.0;
        
        for (Reserva res : reservaSesion) {
            DetalleReservaEntity detRes = new DetalleReservaEntity();
            HabitacionEntity habitacionEntity = habitacionService.buscarHabitacionPorId(res.getProductoId());
            detRes.setHabitacionEntity(habitacionEntity);
            detRes.setCantidad(res.getCantidad());
            detalleReservaEntity.add(detRes);
            totalReserva += res.getCantidad() * habitacionEntity.getPrecio();
        }
        model.addAttribute("carrito", detalleReservaEntity);
        model.addAttribute("total", totalReserva);
        
        return "menu";
    }
    
    @PostMapping("/agregar_habitacion")
    public String agregarHabitacion(HttpSession sesion, @RequestParam("habId") String habId,
                                     @RequestParam("cant") String cant) {
        
        List<Reserva> reservasSesion = null;
        if (sesion.getAttribute("carrito") == null) {
            reservasSesion = new ArrayList<>();
        } else {
            reservasSesion = (List<Reserva>) sesion.getAttribute("carrito");
        }
        
        Integer cantidad = Integer.parseInt(cant);
        Integer habitacionId = Integer.parseInt(habId);
        Reserva reservaNueva = new Reserva(cantidad, habitacionId);
        reservasSesion.add(reservaNueva);
        sesion.setAttribute("carrito", reservasSesion);
        return "redirect:/menu";
    }
    
    @GetMapping("/generar_pdf")
    public ResponseEntity<InputStreamResource> generarPdf(HttpSession sesion) throws IOException {
        // formar los datos para pasarle al pdf
        List<Reserva> reservaSesion = null;
        if (sesion.getAttribute("carrito") == null) {
            reservaSesion = new ArrayList<>();
        } else {
            reservaSesion = (List<Reserva>) sesion.getAttribute("carrito");
        }
        List<DetalleReservaEntity> detalleReservaEntities = new ArrayList<>();
        Double totalReserva = 0.0;
        
        for (Reserva res : reservaSesion) {
            DetalleReservaEntity det = new DetalleReservaEntity();
            HabitacionEntity habitacionEntity = habitacionService.buscarHabitacionPorId(res.getProductoId());
            det.setHabitacionEntity(habitacionEntity);
            det.setCantidad(res.getCantidad());
            detalleReservaEntities.add(det);
            totalReserva += habitacionEntity.getPrecio() * res.getCantidad();
        }
        
        Map<String, Object> datosPdf = new HashMap<>();
        datosPdf.put("factura", detalleReservaEntities);
        datosPdf.put("precio_total", totalReserva);
        
        ByteArrayInputStream pdfBytes = pdfService.generarPdf("template_pdf", datosPdf);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=reservas.pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfBytes));
    }
}
