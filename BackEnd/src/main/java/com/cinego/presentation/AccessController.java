package com.cinego.presentation;

import com.cinego.application.dtos.usuario.UsuarioPrincipalDTO;
import com.cinego.application.dtos.login.LoginRequest;
import com.cinego.application.dtos.token.TokenResponse;
import com.cinego.application.services.TokenService;
import com.cinego.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
