package com.aluracursos.challenge.ForoHub.dto;

public class TopicoResponse {

    private Long id;
    private String titulo;
    private String mensaje;
    private String nombreCurso;
    private String nombreUsuario;
    private Long idUsuario; // Campo adicional para el ID del usuario

    // Constructor completo
    public TopicoResponse(Long id, String titulo, String mensaje, String nombreCurso, String nombreUsuario, Long idUsuario) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.nombreCurso = nombreCurso;
        this.nombreUsuario = nombreUsuario;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
}
