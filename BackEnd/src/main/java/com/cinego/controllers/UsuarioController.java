package com.cinego.controllers;

import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Usuario;
import com.cinego.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Controller de usuarios", description = "Controler responsável pelas ações feitas na entidade Usuarios. Possibilita Criação, Edição, Listagem/Busca e exclusão.")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/listarUsuarios")
    ResponseEntity<?> listarUsuarios(){
        try {
            return ResponseEntity.ok(usuarioService.listarUsuarios());
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    ResponseEntity<?> consultarUsuarioId(
            @PathVariable Long usuarioId
    ){
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioId(usuarioId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> cadastrarUsuario(
            @RequestBody Usuario usuario
    ){
        try {
            return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/editarUsuario/{usuarioId}")
    public ResponseEntity<?> editarUsuario(
            @PathVariable Long usuarioId,
            @RequestBody Usuario usuario
    ){
        try {
            return  ResponseEntity.ok(usuarioService.editarUsuario(usuarioId, usuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @DeleteMapping("/excluirUsuario/{usuarioId}")
    public ResponseEntity<?> excluirUsuario (
            @PathVariable long usuarioId
    ){
        try {
            usuarioService.excluirUsuario(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}

