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
        }
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(
            @RequestBody UsuarioRequestDTO usuario
    ){
        try {
            return ResponseEntity.ok(usuarioService.cadastrarUsuario(usuario));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e){
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/editarUsuario/{usuarioId}")
    public ResponseEntity<UsuarioResponseDTO> editarUsuario(
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
    public ResponseEntity<UsuarioResponseDTO> excluirUsuario (
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

