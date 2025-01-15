package com.aluracursos.challenge.ForoHub.repositorio;

import com.aluracursos.challenge.ForoHub.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepositorio extends JpaRepository<Topico, Long> {

    // Filtrar tópicos por curso
    List<Topico> findByNombreCurso(String nombreCurso);

    // Filtrar tópicos por usuario
//    @Query("SELECT t FROM Topico t WHERE t.usuario.email = :email")
//    List<Topico> findByUsuarioEmail(String email);
    @Query("SELECT t FROM Topico t WHERE t.usuario.id = :usuarioId")
    List<Topico> findByUsuarioId(Long usuarioId);


    // Buscar tópicos por palabra clave en título o mensaje
    @Query("SELECT t FROM Topico t WHERE t.titulo LIKE %:keyword% OR t.mensaje LIKE %:keyword%")
    List<Topico> searchByKeyword(String keyword);


    // Método para encontrar todos los tópicos con paginación
    Page<Topico> findAll(Pageable pageable);
}
