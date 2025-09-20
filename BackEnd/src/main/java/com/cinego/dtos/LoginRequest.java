package com.cinego.dtos;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DTO de login", description = "DTO destinado ao login, para que a requisição envie e receba apenas as informações necessárias, evitando expor demais atributos da entidade desnecessariamente.")
public record LoginRequest (String email, String senha){
}
