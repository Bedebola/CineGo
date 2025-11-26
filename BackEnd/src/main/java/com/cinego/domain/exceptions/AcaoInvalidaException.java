package com.cinego.domain.exceptions;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Exception ações invalidas", description = "Exception personalizada da API CineGo usada para erros de ações inválidas")
public class AcaoInvalidaException extends RuntimeException {
    public AcaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
