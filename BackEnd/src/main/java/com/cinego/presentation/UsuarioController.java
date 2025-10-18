package com.cinego.presentation;

import com.cinego.application.dtos.usuario.UsuarioRequestDTO;
import com.cinego.application.dtos.usuario.UsuarioResponseDTO;
import com.cinego.domain.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Tag(name = "Controller de usuarios", description = "Controler responsável pelas ações feitas na entidade Usuarios. Possibilita Criação, Edição, Listagem/Busca e exclusão.")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/listarUsuarios")
    ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(){
        try {
            return ResponseEntity.ok(usuarioService.consultarTodosUsuariosSemFiltro());
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    ResponseEntity<UsuarioResponseDTO> consultarUsuarioId(
            @PathVariable Long usuarioId
    ){
        try {
            return ResponseEntity.ok(usuarioService.buscarUsuarioId(usuarioId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> cadastrarUsuario(
            @RequestBody UsuarioRequestDTO usuario
    ){
        try {
            UsuarioResponseDTO usuarioCriado = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao cadastrar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/editarUsuario/{usuarioId}")
    public ResponseEntity<?> editarUsuario(
            @PathVariable Long usuarioId,
            @RequestBody UsuarioRequestDTO usuario
    ){
        try {
            UsuarioResponseDTO usuarioEditado = usuarioService.editarUsuario(usuarioId, usuario);
            return ResponseEntity.ok(usuarioEditado);
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno ao editar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluirUsuario/{usuarioId}")
    public ResponseEntity<?> excluirUsuario (
            @PathVariable long usuarioId
    ){
        try {
            usuarioService.excluirUsuario(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: " + e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno no servidor: " + e.getMessage());
        }
    }
}
