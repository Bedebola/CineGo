package com.cinego.application.dtos;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "DTO Token", description = "DTO usado para retornar o token em uma requisição")
public record TokenResponse(String token) {
}
