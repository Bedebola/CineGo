package com.cinego.controllers;

import com.cinego.models.Usuario;
import com.cinego.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> consultarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/listarTodos")
    ResponseEntity<?> listarTodos(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/usuario/{id}")
    ResponseEntity<?> consultarUsuarioId(
            @PathVariable Long id
    ){
            var usuario = usuarioRepository.findById(id)
                    .orElse(null);
            if (usuario == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuario);
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarUsuario(
            @RequestBody Usuario usuario
    ){
        try{
            var UsuarioResponse = usuarioRepository.save(usuario);
            return ResponseEntity.ok(UsuarioResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}

