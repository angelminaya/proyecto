package pe.com.cibertec.lp2_costa_azul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import pe.com.cibertec.lp2_costa_azul.model.entity.ClienteEntity;
import pe.com.cibertec.lp2_costa_azul.service.ClienteService;

@Controller
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping("/registrar_cliente")
    public String mostrarRegistrarCliente(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        return "registrar_cliente";
    }

    @PostMapping("/registrar_cliente")
    public String registrarCliente(@ModelAttribute("cliente") ClienteEntity clienteFormulario,
                                   Model model, @RequestParam("foto") MultipartFile foto) {
        clienteService.crearUsuario(clienteFormulario, foto);
        return "registrar_cliente";
    }

    @GetMapping("/")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new ClienteEntity());
        return "login";    
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("cliente") ClienteEntity clienteFormulario,
                        Model model, HttpSession session) {
        boolean validarCliente = clienteService.validarUsuario(clienteFormulario);
        if (validarCliente) {
            session.setAttribute("usuario", clienteFormulario.getCorreo());
            return "redirect:/menu";
        }
        model.addAttribute("loginInvalido", "No existe el usuario");
        model.addAttribute("cliente", new ClienteEntity());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
