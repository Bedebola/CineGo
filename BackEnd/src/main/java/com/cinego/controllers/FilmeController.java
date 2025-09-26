package com.cinego.controllers;

import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Filme;
import com.cinego.services.FilmeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/filme")
@Tag(name = "Controller de Filmes", description = "Controler responsável pelas ações feitas na entidade Filmes. Possibilita o CRUDE Basico (Criação, Edição, Listagem/Busca e exclusão) e também ações como alugar, devolver e destivar através do método PUT.")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/listarFilmes")
    ResponseEntity<?> listarFilmes(){
        try {
            return ResponseEntity.ok(filmeService.listarFilmes());
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/buscarFilmeId/{filmeId}")
    ResponseEntity<?> buscarFilmePorId(
            @PathVariable Long filmeId
    ){
        try {
            return ResponseEntity.ok(filmeService.buscarFilmePorId(filmeId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/cadastrarFilme")
    ResponseEntity<?> cadastrarFilme(
            @RequestBody Filme filme
    ){
        try{
            return ResponseEntity.ok(filmeService.cadastrarFilme(filme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("editarFilme/{filmeId}")
    ResponseEntity<?> editarfilme(
            @PathVariable Long filmeId,
            @RequestBody Filme filme
    ){
        try {
            return ResponseEntity.ok(filmeService.editarFilme(filmeId, filme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/alugarFilme/{filmeId}")
    ResponseEntity<?> alugarFilme(
            @PathVariable Long filmeId
    ){
        try {
            return ResponseEntity.ok(filmeService.alugarFilme(filmeId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/devolverFilme/{filmeId}")
    ResponseEntity<?> devolverFilme(
            @PathVariable Long filmeId
    ){
        try {
            return ResponseEntity.ok(filmeService.devolverFilme(filmeId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/desativarFilme/{filmeId}")
    ResponseEntity<?> desativarFilme(
            @PathVariable Long filmeId
    ){
        try {
            return ResponseEntity.ok(filmeService.desativarFilme(filmeId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/ativarFilme/{filmeId}")
    ResponseEntity<?> ativarFilme(
            @PathVariable Long filmeId
    ){
        try {
            return ResponseEntity.ok(filmeService.ativarFilme(filmeId));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @DeleteMapping("/excluirFilme/{filmeId}")
    public ResponseEntity<?> excluirFilme (
            @PathVariable Long filmeId
    ){
        try {
            filmeService.excluirFilme(filmeId);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}
