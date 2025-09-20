package com.cinego.controllers;

import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Usuario;
import com.cinego.services.UsuarioService;
import com.cinego.repositories.UsuarioRepository;
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

    @GetMapping("/usuario/{idUsuario}")
    ResponseEntity<?> consultarUsuarioId(
            @PathVariable Long idUsuario
    ){
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioId(idUsuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarUsuario(
            @RequestBody Usuario usuario
    ){
        try {
            return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/editar/{idUsuario}")
    public ResponseEntity<?> editarUsuario(
            @PathVariable Long idUsuario,
            @RequestBody Usuario usuario
    ){
        try {
            return  ResponseEntity.ok(usuarioService.editarUsuario(idUsuario, usuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @DeleteMapping("/excluir/{idUsuario}")
    public ResponseEntity<?> excluirUsuario (
            @PathVariable long idUsuario
    ){
        try {
            usuarioService.excluirUsuario(idUsuario);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}

