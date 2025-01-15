package com.aluracursos.challenge.ForoHub.controlador;

import com.aluracursos.challenge.ForoHub.dto.RespuestaRequest;
import com.aluracursos.challenge.ForoHub.dto.RespuestaResponse;
import com.aluracursos.challenge.ForoHub.dto.RespuestaUpdateRequest;
import com.aluracursos.challenge.ForoHub.modelo.Respuesta;
import com.aluracursos.challenge.ForoHub.modelo.Topico;
import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import com.aluracursos.challenge.ForoHub.repositorio.RespuestaRepositorio;
import com.aluracursos.challenge.ForoHub.repositorio.TopicoRepositorio;
import com.aluracursos.challenge.ForoHub.repositorio.UsuarioRepositorio;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaControlador {

    private final RespuestaRepositorio respuestaRepositorio;
    private final TopicoRepositorio topicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public RespuestaControlador(RespuestaRepositorio respuestaRepositorio, TopicoRepositorio topicoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.respuestaRepositorio = respuestaRepositorio;
        this.topicoRepositorio = topicoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @PostMapping
    public RespuestaResponse crearRespuesta(@RequestBody @Valid RespuestaRequest respuestaRequest, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Usuario usuario = usuarioRepositorio.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Topico topico = topicoRepositorio.findById(respuestaRequest.getTopicoId())
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));

        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(respuestaRequest.getMensaje());
        respuesta.setTopico(topico);
        respuesta.setUsuario(usuario);

        Respuesta respuestaGuardada = respuestaRepositorio.save(respuesta);

        return new RespuestaResponse(
                respuestaGuardada.getId(),
                respuestaGuardada.getMensaje(),
                usuario.getNombre(),
                respuestaGuardada.getFechaCreacion(),
                respuestaGuardada.getSolucion()
        );
    }

    @GetMapping("/{topicoId}")
    public List<RespuestaResponse> listarRespuestasPorTopico(@PathVariable Long topicoId) {
        return respuestaRepositorio.findAll().stream()
                .filter(respuesta -> respuesta.getTopico().getId().equals(topicoId))
                .map(respuesta -> new RespuestaResponse(
                        respuesta.getId(),
                        respuesta.getMensaje(),
                        respuesta.getUsuario().getNombre(),
                        respuesta.getFechaCreacion(),
                        respuesta.getSolucion()
                )).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public RespuestaResponse actualizarRespuesta(@PathVariable Long id, @RequestBody RespuestaUpdateRequest respuestaUpdateRequest, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Respuesta respuesta = respuestaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        // Verificar que el usuario autenticado es el autor de la respuesta
        if (!respuesta.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tienes permiso para actualizar esta respuesta");
        }

        respuesta.setMensaje(respuestaUpdateRequest.getMensaje());
        Respuesta respuestaActualizada = respuestaRepositorio.save(respuesta);

        return new RespuestaResponse(
                respuestaActualizada.getId(),
                respuestaActualizada.getMensaje(),
                respuestaActualizada.getUsuario().getNombre(),
                respuestaActualizada.getFechaCreacion(),
                respuestaActualizada.getSolucion()
        );
    }


    @DeleteMapping("/{id}")
    public void eliminarRespuesta(@PathVariable Long id, Authentication authentication) {
        String emailUsuario = authentication.getName();
        Respuesta respuesta = respuestaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        // Verificar que el usuario autenticado es el autor de la respuesta
        if (!respuesta.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tienes permiso para eliminar esta respuesta");
        }

        respuestaRepositorio.deleteById(id);
    }


    @GetMapping("/soluciones")
    public List<RespuestaResponse> listarRespuestasSolucion() {
        return respuestaRepositorio.findBySolucionTrue().stream().map(respuesta -> new RespuestaResponse(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getUsuario().getNombre(),
                respuesta.getFechaCreacion(),
                respuesta.getSolucion()
        )).toList();
    }

}
