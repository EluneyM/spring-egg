package com.egg.demo.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.egg.demo.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, Long> {

}
