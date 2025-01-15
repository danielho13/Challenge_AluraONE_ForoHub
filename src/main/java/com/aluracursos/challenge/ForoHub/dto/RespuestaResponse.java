package com.aluracursos.challenge.ForoHub.dto;

import java.time.LocalDateTime;

public class RespuestaResponse {

    private Long id;
    private String mensaje;
    private String nombreUsuario;
    private LocalDateTime fechaCreacion;
    private Boolean solucion;

    // Constructor
    public RespuestaResponse(Long id, String mensaje, String nombreUsuario, LocalDateTime fechaCreacion, Boolean solucion) {
        this.id = id;
        this.mensaje = mensaje;
        this.nombreUsuario = nombreUsuario;
        this.fechaCreacion = fechaCreacion;
        this.solucion = solucion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getSolucion() {
        return solucion;
    }

    public void setSolucion(Boolean solucion) {
        this.solucion = solucion;
    }
}
