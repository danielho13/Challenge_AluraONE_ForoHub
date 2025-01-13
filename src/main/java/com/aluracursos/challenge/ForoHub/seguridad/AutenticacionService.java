package com.aluracursos.challenge.ForoHub.seguridad;

import com.aluracursos.challenge.ForoHub.modelo.Usuario;
import com.aluracursos.challenge.ForoHub.repositorio.UsuarioRepositorio;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    public AutenticacionService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getContrasena()) // Recuerda almacenar la contraseña encriptada
                .roles("USER")
                .build();
    }
}
