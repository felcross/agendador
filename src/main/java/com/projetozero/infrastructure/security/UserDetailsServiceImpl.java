package com.projetozero.infrastructure.security;



import com.projetozero.controller.dto.UsuarioDTO;
import com.projetozero.infrastructure.client.UsuarioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UsuarioClient usuarioClient;

    // ✅ Use este método APENAS no login, não no filtro JWT!
    public UserDetails carregaDadosUsuario(String email, String token) {
        UsuarioDTO client = usuarioClient.buscarUsuarioPorEmail(email, token);

        return org.springframework.security.core.userdetails.User
                .withUsername(client.getEmail())
                .password(client.getSenha())
                .build();
    }
}
