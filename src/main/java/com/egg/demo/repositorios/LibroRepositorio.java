package com.egg.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.demo.entidades.Libro;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long>{

}
