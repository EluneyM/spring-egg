package com.egg.demo.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.egg.demo.entidades.Usuario;
import com.egg.demo.enumeraciones.Rol;
import com.egg.demo.exceptiones.UsuarioRegistroException;
import com.egg.demo.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public void registrar(String nombre, String email, String password, String password2) throws Exception {
        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    private void validar(String nombre, String email, String password, String password2)
            throws UsuarioRegistroException {

        if (nombre == null || nombre.isEmpty()) {
            throw new UsuarioRegistroException("el nombre no puede ser nulo o estar vacío");
        }
        if (email == null || email.isEmpty()) {
            throw new UsuarioRegistroException("el email no puede ser nulo o estar vacío");
        }
        if (password == null || password.length() <= 5 || password.isEmpty()) {
            throw new UsuarioRegistroException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new UsuarioRegistroException("Las contraseñas ingresadas deben ser iguales");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        } else {
            return null;
        }
    }

}
