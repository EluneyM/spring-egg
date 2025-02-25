package com.egg.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.demo.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

}
