package com.cinego.application.dtos.usuario;

import com.cinego.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DTO Usuario", description = "DTO desitnado a transição de dados da entidade usuario entre requests e responses")
public record UsuarioResponseDTO(Long usuarioId, String nome, String cpf, String email, String role){

    public UsuarioResponseDTO(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getRole()
        );
    }

}
