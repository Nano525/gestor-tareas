package mx.edu.utez.gestor_tareas.controller;

import mx.edu.utez.gestor_tareas.Service.UsuarioService;
import mx.edu.utez.gestor_tareas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String mostrarLogin(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (usuarioService.validarCredenciales(username, password)) {
            Usuario usuario = usuarioService.buscarPorUsername(username);
            session.setAttribute("usuario", usuario);
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioNombre", usuario.getNombre());
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Usuario o contraseña incorrectos");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String nombre,
            @RequestParam String email,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        Usuario nuevoUsuario = usuarioService.registrarUsuario(username, password, nombre, email);
        
        if (nuevoUsuario != null) {
            session.setAttribute("usuario", nuevoUsuario);
            session.setAttribute("usuarioId", nuevoUsuario.getId());
            session.setAttribute("usuarioNombre", nuevoUsuario.getNombre());
            redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "El usuario ya existe");
            return "redirect:/registro";
        }
    }
}

