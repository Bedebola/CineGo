package com.cinego.exceptions;

import io.swagger.v3.oas.annotations.tags.Tag;

public class AcaoInvalidaException extends RuntimeException {
    public AcaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
