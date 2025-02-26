package com.egg.demo.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.demo.entidades.Autor;
import com.egg.demo.repositorios.AutorRepositorio;

@Service
public class AutorServicio {
    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) {
        Autor autor = new Autor();// Instancio un objeto del tipo Autor
        autor.setNombre(nombre);// Seteo el atributo, con el valor recibido como parámetro
        autorRepositorio.save(autor); // Persisto el dato en mi BBDD
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<>();

        autores = autorRepositorio.findAll();

        return autores;
    }

    @Transactional
    public void modificarAutor(String nombre, String id) {
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (!respuesta.isPresent()) {
            return;
        }

        Autor autor = respuesta.get();

        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }
}
