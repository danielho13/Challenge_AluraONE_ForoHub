package com.aluracursos.challenge.ForoHub.repositorio;

import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
