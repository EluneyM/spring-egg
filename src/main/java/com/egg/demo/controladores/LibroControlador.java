package com.egg.demo.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.demo.entidades.Autor;
import com.egg.demo.entidades.Editorial;
import com.egg.demo.servicios.AutorServicio;
import com.egg.demo.servicios.EditorialServicio;
import com.egg.demo.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap model) {

        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("autores", autores);
        model.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(
            @RequestParam(required = false) Long isbn,
            @RequestParam String titulo,
            @RequestParam(required = false) int ejemplares,
            @RequestParam String autorId,
            @RequestParam Long editorialId,
            ModelMap modelMap) {
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, autorId, editorialId);
        } catch (Exception e) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, e);
            modelMap.put("error", "Ocurrió un error");

            return "libro_form.html";
        }
        return "index.html";
    }
}
