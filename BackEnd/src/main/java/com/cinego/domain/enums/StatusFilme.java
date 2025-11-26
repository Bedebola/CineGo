package com.cinego.domain.enums;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Enum Status Filme", description = "Enum destinado a estabelecer e padronizar os estados em que a entidde Filme pode estar.")
public enum StatusFilme {
    DISPONIVEL,
    ALUGADO,
    DESATIVADO
}