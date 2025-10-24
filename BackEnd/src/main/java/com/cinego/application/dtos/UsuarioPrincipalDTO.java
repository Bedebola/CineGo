package com.cinego.application.dtos;

import com.cinego.domain.entities.Usuario;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UsuarioPrincipalDTO (Long id, String email, Collection<? extends GrantedAuthority> autorizacao){

    public UsuarioPrincipalDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getAuthorities()
        );
    }

}
