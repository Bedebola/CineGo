package com.cinego.services;

import com.cinego.dtos.LoginRequest;
import com.cinego.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequest login){
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());

    }
}
