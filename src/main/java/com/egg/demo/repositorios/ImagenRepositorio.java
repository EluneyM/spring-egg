package com.egg.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.demo.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String>{

}
