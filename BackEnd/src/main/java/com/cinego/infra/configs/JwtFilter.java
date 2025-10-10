package com.cinego.infra.configs;

import com.cinego.application.services.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Tag(name = "Filtro JWT", description = "Classe de aplicação do filtro de segurança")
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        try {
            if ( path.equals("/access/login")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/webjars")
                || path.startsWith("/")

            ) {
                filterChain.doFilter(request, response);
                return;
            }

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")){
                String token = header.replace("Bearer ", "");
                var usuario = tokenService.validarToken(token);

                var autorizacao = new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(),
                        null,
                        usuario.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(autorizacao);
                //SecurityContextHolder.getContext().getAuthentication().getPrincipal(); -> retorna o usuario que fez a requisição
                filterChain.doFilter(request, response);

            }  else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Token nao informado!");
                return;
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Erro no token: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
