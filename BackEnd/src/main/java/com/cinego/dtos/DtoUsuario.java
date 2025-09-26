package com.cinego.dtos;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "DTO Usuario", description = "DTO desitnado a transição de dados da entidade usuario entre requests e responses")
public class DtoUsuario {

    private Long usuarioId;
    private String nome;
    private String cpf;
    private String email;
    private String role;
}
