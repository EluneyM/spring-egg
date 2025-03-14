package com.egg.demo.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.demo.entidades.Usuario;
import com.egg.demo.enumeraciones.Rol;
import com.egg.demo.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "inicio.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2,
            ModelMap model) {
        try {
            usuarioServicio.registrar(nombre, email, password, password2);

            model.put("exito", "Usuario registrado con éxito!");

            return "login.html";
        } catch (Exception e) {

            model.put("error", e.getMessage());
            model.put("nombre", nombre);
            model.put("email", email);
            return "registro.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña inválidos!");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuariosession");

        if (usuarioLogueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }
}
