package com.aluracursos.challenge.ForoHub.dto;

public class RespuestaRequest {

    private String mensaje;
    private Long topicoId;

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getTopicoId() {
        return topicoId;
    }

    public void setTopicoId(Long topicoId) {
        this.topicoId = topicoId;
    }
}
