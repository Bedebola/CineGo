package com.cinego.application.dtos.usuario;

public record UsuarioRequestDTO(Long usuarioId, String nome, String cpf, String email, String role, String senha) {

}

