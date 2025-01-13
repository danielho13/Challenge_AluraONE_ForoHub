package com.aluracursos.challenge.ForoHub;

import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import com.aluracursos.challenge.ForoHub.repositorio.UsuarioRepositorio;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ForoHubApplication {

	private final UsuarioRepositorio usuarioRepositorio;
	private final PasswordEncoder passwordEncoder;

	public ForoHubApplication(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
		this.usuarioRepositorio = usuarioRepositorio;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(ForoHubApplication.class, args);
	}


}
