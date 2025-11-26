package com.cinego.domain.exceptions;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Exception Argumentos invalidos", description = "Exception personalizada da API CineGo usada para erros de arumentos inválidos ou não localizados")
public class ArgumentoInvalidoOuNaoEncontradoException extends Exception{
    public ArgumentoInvalidoOuNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
