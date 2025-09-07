package com.cinego.exceptions;

public class AcaoInvalidaException extends RuntimeException {
    public AcaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
