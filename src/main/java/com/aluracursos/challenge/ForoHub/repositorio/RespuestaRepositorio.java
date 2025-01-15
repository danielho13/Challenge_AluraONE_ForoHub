package com.aluracursos.challenge.ForoHub.repositorio;

import com.aluracursos.challenge.ForoHub.modelo.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepositorio extends JpaRepository<Respuesta, Long> {

    // Filtrar respuestas marcadas como solución
    List<Respuesta> findBySolucionTrue();
}
