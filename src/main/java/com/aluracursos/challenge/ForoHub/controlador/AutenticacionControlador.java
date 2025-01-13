package com.aluracursos.challenge.ForoHub.controlador;

import com.aluracursos.challenge.ForoHub.seguridad.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AutenticacionControlador {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacionControlador(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String contrasena = request.get("contrasena");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, contrasena)
        );

        String token = tokenService.generarToken(authentication);
        return Map.of("token", token);
    }
}
