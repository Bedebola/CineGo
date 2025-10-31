package com.cinego.application.dtos.usuario;

import com.cinego.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Tag(name = "DTO Usuario", description = "DTO desitnado a transição de dados da entidade usuario entre requests e responses")
public record UsuarioPrincipalDTO (Long id, String email, Collection<? extends GrantedAuthority> autorizacao){

    public UsuarioPrincipalDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getAuthorities()
        );
    }

}
