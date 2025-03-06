package com.egg.demo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.demo.entidades.Editorial;
import com.egg.demo.exceptiones.EditorialNoEncontradaException;
import com.egg.demo.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) {
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList<>();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    public void modificarEditorial(String nombre, Long id) throws Exception {
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (!respuesta.isPresent()) {
            throw new EditorialNoEncontradaException("La editorial no existe.");
        }

        Editorial editorial = respuesta.get();
        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    @Transactional(readOnly = true)
    public Editorial getOne(Long id) {
        return editorialRepositorio.getReferenceById(id);
    }
}
