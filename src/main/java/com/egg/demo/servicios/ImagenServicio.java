package com.egg.demo.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.demo.entidades.Imagen;
import com.egg.demo.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {
    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(MultipartFile archivo) throws Exception {
        if (archivo == null) {
            return null;
        }

        try {
            Imagen imagen = new Imagen();

            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());

            return imagenRepositorio.save(imagen);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Imagen actualizar(MultipartFile archivo, String imagenId) throws Exception {

        if (archivo == null) {
            return null;
        }

        try {
            Imagen imagen = new Imagen();

            if (imagenId != null) {
                Optional<Imagen> respuesta = imagenRepositorio.findById(imagenId);
                if (respuesta.isPresent()) {
                    imagen = respuesta.get();
                }
            }

            imagen.setMime(archivo.getContentType());
            imagen.setNombre(archivo.getName());
            imagen.setContenido(archivo.getBytes());

            return imagenRepositorio.save(imagen);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
