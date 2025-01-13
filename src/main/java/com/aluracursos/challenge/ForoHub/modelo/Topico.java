package com.aluracursos.challenge.ForoHub.modelo;

import jakarta.persistence.*;

@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @Column(name = "nombre_curso")
    private String nombreCurso;

    @Column(name = "id_usuario")
    private Long idUsuario;

    // Constructor vacío (requerido por JPA)
    public Topico() {}

    // Constructor
    public Topico(String titulo, String mensaje, String nombreCurso, Long idUsuario) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.nombreCurso = nombreCurso;
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

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Getter y Setter para usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

