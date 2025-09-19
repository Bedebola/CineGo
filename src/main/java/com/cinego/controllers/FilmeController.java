package com.cinego.controllers;

import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Filme;
import com.cinego.services.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/filme")
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

    @GetMapping("/buscarFilmeId/{id}")
    ResponseEntity<?> buscarFilmePorId(
            @PathVariable Long idFilme
    ){
        try {
            return ResponseEntity.ok(filmeService.buscarFilmePorId(idFilme));
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

    @PutMapping("editarFilme/{idFilme}")
    ResponseEntity<?> editarfilme(
            @PathVariable Long idFilme,
            @RequestBody Filme filme
    ){
        try {
            return ResponseEntity.ok(filmeService.editarFilme(idFilme, filme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/alugarFilme/{idFilme}")
    ResponseEntity<?> alugarFilme(
            @PathVariable Long idFilme
    ){
        try {
            return ResponseEntity.ok(filmeService.alugarFilme(idFilme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/devolverFilme/{idFilme}")
    ResponseEntity<?> devolverFilme(
            @PathVariable Long idFilme
    ){
        try {
            return ResponseEntity.ok(filmeService.devolverFilme(idFilme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/desativarFilme/{idFilme}")
    ResponseEntity<?> desativarFilme(
            @PathVariable Long idFilme
    ){
        try {
            return ResponseEntity.ok(filmeService.desativarFilme(idFilme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PutMapping("/ativarFilme/{idFilme}")
    ResponseEntity<?> ativarFilme(
            @PathVariable Long idFilme
    ){
        try {
            return ResponseEntity.ok(filmeService.ativarFilme(idFilme));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @DeleteMapping("/excluir/{idFilme}")
    public ResponseEntity<?> excluirFilme (
            @PathVariable Long idFilme
    ){
        try {
            filmeService.excluirFilme(idFilme);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor");
        }
    }
}
