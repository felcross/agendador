package com.projetozero.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component  // ✅ Adicione @Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // ✅ REMOVA o UserDetailsService - não precisa!
    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Extrai o token (remove "Bearer ")
                final String token = authorizationHeader.substring(7);

                // Extrai o username (email) do token
                final String username = jwtUtil.extractUsername(token);

                // Se o username não for nulo e não houver autenticação ainda
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // ✅ Valida o token
                    if (jwtUtil.validateToken(token, username)) {

                        // ✅ Cria um UserDetails simples (não precisa buscar no banco!)
                        UserDetails userDetails = User.builder()
                                .username(username)
                                .password("")  // Senha vazia (já validou pelo token)
                                .authorities(new ArrayList<>())  // Sem roles por enquanto
                                .build();

                        // Cria o objeto de autenticação
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                        // Define a autenticação no contexto
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Log do erro mas continua a requisição
                logger.error("Erro ao processar token JWT: " + e.getMessage());
            }
        }

        // Continua a cadeia de filtros
        chain.doFilter(request, response);
    }
}