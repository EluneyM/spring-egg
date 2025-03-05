package com.egg.demo.servicios;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egg.demo.entidades.Autor;
import com.egg.demo.entidades.Editorial;
import com.egg.demo.entidades.Libro;
import com.egg.demo.exceptiones.AutorNoEncontradoException;
import com.egg.demo.exceptiones.EditorialNoEncontradaException;
import com.egg.demo.exceptiones.LibroNoEncontradoException;
import com.egg.demo.repositorios.AutorRepositorio;
import com.egg.demo.repositorios.EditorialRepositorio;
import com.egg.demo.repositorios.LibroRepositorio;

@Service
public class LibroServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private LibroRepositorio libroRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, int ejemplares, String autorId, Long editorialId)
            throws Exception {
        Libro libro = new Libro();
        if (isbn == null) {
            throw new InvalidParameterException("Isbn es nulo.");
        }
        if (titulo == null) {
            throw new InvalidParameterException("titulo es nulo.");
        }
        if (autorId == null) {
            throw new InvalidParameterException("autor ID es nulo.");
        }
        if (editorialId == null) {
            throw new InvalidParameterException("editorial ID es nulo.");
        }
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());

        Autor autor = autorRepositorio.findById(autorId).get();
        Editorial editorial = editorialRepositorio.findById(editorialId).get();

        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {

        List<Libro> libros = new ArrayList<>();

        libros = libroRepositorio.findAll();
        return libros;
    }

    public void modificarLibro(String titulo, int ejemplares, Long isbn, String autorId, Long editorialId)
            throws Exception {

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);
        Optional<Autor> autorRespuesta = autorRepositorio.findById(autorId);
        Optional<Editorial> editorialRespuesta = editorialRepositorio.findById(editorialId);

        this.validar(respuesta, editorialRespuesta, autorRespuesta);

        Libro libro = respuesta.get();
        Editorial editorial = editorialRespuesta.get();
        Autor autor = autorRespuesta.get();

        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    private void validar(Optional<Libro> l, Optional<Editorial> e, Optional<Autor> a) throws Exception {
        if (!l.isPresent()) {
            throw new LibroNoEncontradoException("El libro no existe.");
        }

        if (!a.isPresent()) {
            throw new AutorNoEncontradoException("El autor no existe.");
        }

        if (!e.isPresent()) {
            throw new EditorialNoEncontradaException("La editorial no existe.");
        }
    }
}
