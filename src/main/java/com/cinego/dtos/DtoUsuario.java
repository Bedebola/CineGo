package com.cinego.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUsuario {

    private String nome;
    private String cpf;
    private String email;
}
