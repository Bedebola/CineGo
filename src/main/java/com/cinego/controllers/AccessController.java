package com.cinego.controllers;

import com.cinego.dtos.LoginRequest;
import com.cinego.dtos.TokenResponse;
import com.cinego.services.TokenService;
import com.cinego.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/access")
@Tag(name = "Controller de Autenticação", description = "Controler responsável pela autenticação do login")
public class AccessController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "login", description = "metodo de login de usuarios")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ){
        if (!usuarioService.validarSenha(request)){
            return ResponseEntity.badRequest().body("Usuario ou senha invalidos");
        }

        var token = tokenService.gerarToken(request);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
