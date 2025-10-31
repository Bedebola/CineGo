package com.cinego.application.dtos.usuario;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DTO de requisição de Usuario", description = "DTO desitnado a transição de dados da entidade usuario nas respostas")
public record UsuarioRequestDTO(String nome, String cpf, String email, String role, String senha) {

}

