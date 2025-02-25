package com.egg.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egg.demo.entidades.Editorial;

public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {

}
