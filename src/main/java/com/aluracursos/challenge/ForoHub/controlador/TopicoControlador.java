package com.aluracursos.challenge.ForoHub.controlador;

import com.aluracursos.challenge.ForoHub.dto.TopicoRequest;
import com.aluracursos.challenge.ForoHub.dto.TopicoResponse;
import com.aluracursos.challenge.ForoHub.dto.TopicoUpdateRequest;
import com.aluracursos.challenge.ForoHub.modelo.Topico;
import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import com.aluracursos.challenge.ForoHub.repositorio.TopicoRepositorio;
import com.aluracursos.challenge.ForoHub.repositorio.UsuarioRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoControlador {

    private final TopicoRepositorio topicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public TopicoControlador(TopicoRepositorio topicoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.topicoRepositorio = topicoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }
    @GetMapping
    public Page<TopicoResponse> listarTopicos(@PageableDefault(size = 10) Pageable pageable) {
        return topicoRepositorio.findAll(pageable).map(topico -> new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                topico.getUsuario() != null ? topico.getUsuario().getNombre() : "Usuario desconocido",
                topico.getUsuario() != null ? topico.getUsuario().getId() : null
        ));
    }

    @GetMapping("/filtros")
    public List<TopicoResponse> filtrarTopicos(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String keyword) {

        List<Topico> topicos;

        if (curso != null) {
            topicos = topicoRepositorio.findByNombreCurso(curso);
        } else if (usuarioId != null) {
            topicos = topicoRepositorio.findByUsuarioId(usuarioId);
        } else if (keyword != null) {
            topicos = topicoRepositorio.searchByKeyword(keyword);
        } else {
            topicos = topicoRepositorio.findAll();
        }

        return topicos.stream().map(topico -> new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                topico.getUsuario() != null ? topico.getUsuario().getNombre() : "Usuario desconocido",
                topico.getUsuario() != null ? topico.getUsuario().getId() : null
        )).toList();
    }



    @PostMapping
    public TopicoResponse crearTopico(@RequestBody @Valid TopicoRequest topicoRequest, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Usuario usuario = usuarioRepositorio.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico nuevoTopico = new Topico(
                topicoRequest.getTitulo(),
                topicoRequest.getMensaje(),
                topicoRequest.getNombreCurso(),
                usuario.getId()
        );
        nuevoTopico.setUsuario(usuario);

        Topico topicoGuardado = topicoRepositorio.save(nuevoTopico);

        return new TopicoResponse(
                topicoGuardado.getId(),
                topicoGuardado.getTitulo(),
                topicoGuardado.getMensaje(),
                topicoGuardado.getNombreCurso(),
                usuario.getNombre(),
                usuario.getId()
        );
    }


    @PutMapping("/{id}")
    public TopicoResponse actualizarTopico(@PathVariable Long id, @RequestBody TopicoUpdateRequest topicoUpdateRequest, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Topico topico = topicoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        // Verificar que el usuario autenticado es el autor del tópico
        if (!topico.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tienes permiso para actualizar este tópico");
        }

        topico.setTitulo(topicoUpdateRequest.getTitulo());
        topico.setMensaje(topicoUpdateRequest.getMensaje());
        Topico topicoActualizado = topicoRepositorio.save(topico);

        return new TopicoResponse(
                topicoActualizado.getId(),
                topicoActualizado.getTitulo(),
                topicoActualizado.getMensaje(),
                topicoActualizado.getNombreCurso(),
                topicoActualizado.getUsuario().getNombre(),
                topicoActualizado.getUsuario().getId()
        );
    }




    @DeleteMapping("/{id}")
    public void eliminarTopico(@PathVariable Long id, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Topico topico = topicoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        // Verificar que el usuario autenticado es el autor del tópico
        if (!topico.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tienes permiso para eliminar este tópico");
        }

        topicoRepositorio.deleteById(id);
    }


}
