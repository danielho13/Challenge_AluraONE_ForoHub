package com.aluracursos.challenge.ForoHub.controlador;

import com.aluracursos.challenge.ForoHub.dto.TopicoRequest;
import com.aluracursos.challenge.ForoHub.dto.TopicoResponse;
import com.aluracursos.challenge.ForoHub.dto.TopicoUpdateRequest;
import com.aluracursos.challenge.ForoHub.modelo.Topico;
import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import com.aluracursos.challenge.ForoHub.repositorio.TopicoRepositorio;
import com.aluracursos.challenge.ForoHub.repositorio.UsuarioRepositorio;
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
    public List<TopicoResponse> listarTopicos() {
        return topicoRepositorio.findAll().stream().map(topico -> new TopicoResponse(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getNombreCurso(),
                topico.getUsuario() != null ? topico.getUsuario().getNombre() : "Usuario desconocido", // Nombre del usuario o "desconocido"
                topico.getUsuario() != null ? topico.getUsuario().getId() : null // ID del usuario o null
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
    public TopicoResponse actualizarTopico(@PathVariable Long id, @RequestBody TopicoUpdateRequest topicoUpdateRequest) {
        Topico topico = topicoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        topico.setTitulo(topicoUpdateRequest.getTitulo());
        topico.setMensaje(topicoUpdateRequest.getMensaje());
        Topico topicoActualizado = topicoRepositorio.save(topico);

        return new TopicoResponse(
                topicoActualizado.getId(),
                topicoActualizado.getTitulo(),
                topicoActualizado.getMensaje(),
                topicoActualizado.getNombreCurso(),
                topicoActualizado.getUsuario() != null ? topicoActualizado.getUsuario().getNombre() : "Usuario desconocido",
                topicoActualizado.getUsuario() != null ? topicoActualizado.getUsuario().getId() : null
        );
    }



    @DeleteMapping("/{id}")
    public void eliminarTopico(@PathVariable Long id) {
        topicoRepositorio.deleteById(id);
    }

}
