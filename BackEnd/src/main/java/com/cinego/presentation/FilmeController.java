package com.cinego.presentation;

import com.cinego.application.dtos.filme.FilmeDTO;
import com.cinego.application.dtos.usuario.UsuarioPrincipalDTO;
import com.cinego.application.services.RegistroLocacaoService;
import com.cinego.domain.enums.StatusFilme;
import com.cinego.domain.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.domain.entities.Filme;
import com.cinego.application.services.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/filme")
@Tag(name = "Controller de Filmes", description = "Controler responsável pelas ações feitas na entidade Filmes. Possibilita o CRUDE Basico (Criação, Edição, Listagem/Busca e exclusão) e também ações como alugar, devolver e destivar através do método PUT.")
public class    FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private RegistroLocacaoService registroLocacaoService;

    @Operation(
            summary = "Listar Filmes sem filtro",
            description = "Lista todos os filmes do repositório sem aplicação de filtros específicos"
    )
    @GetMapping("/listarFilmes")
    ResponseEntity<?> listarFilmes(){
        try {
            return ResponseEntity.ok(filmeService.listarFilmes());
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Operation(
            summary = "Listar filmes por status",
            description = "Lista os filmes cadastrados no repositório filtrando pelo status selecionado pelo usuario"
    )
    @GetMapping("/listarFilmesPorStatus/{status}")
    ResponseEntity<?> listarFilmesPorStatus(
            @PathVariable StatusFilme status
            ){
        try {
            return ResponseEntity.ok(filmeService.listarFilmesPorStatus(status));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Operation(
            summary = "Buscar filme por id",
            description = "busca um filme especifico pelo ID informado pelo usuario"
    )
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

    @Operation(
            summary = "Cadastrar Filme",
            description = "Faz o cadastro de um novo filme no banco de dados"
    )
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

    @Operation(
            summary = "Editar Filme",
            description = "Faz a edição de um filme existente no banco de dados"
    )
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

    @Operation(
            summary = "Alugar Filme",
            description = "Altera o status do filme para Alugado"
    )

    @PutMapping("/alugarFilme/{filmeId}")
    ResponseEntity<?> alugarFilme(
            @PathVariable Long filmeId,
            @RequestBody String emailCliente,
            @AuthenticationPrincipal UsuarioPrincipalDTO usuarioLogado

    ){
        try {
            return ResponseEntity.ok(filmeService.alugarFilme(filmeId, emailCliente, usuarioLogado));
        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            return ResponseEntity.badRequest().body("Erro: " + e);
        }
    }

    @PostMapping("/enviarEmailLembreteDeDevolucao/{filmeId}")
    @Operation(summary = "Envio de email para lembrar o cliente de devolver o filme alugado.")
    public ResponseEntity<?> enviarEmailLembreteDeDevolucaoo(
            @PathVariable Long filmeId
    ){

        try {
            registroLocacaoService.enviarEmailDevolucao(filmeId);
            return ResponseEntity.ok("Email enviado com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Operation(
            summary = "Devolver Filme",
            description = "Altera o status do filme para Disponivel"
    )
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

    @Operation(
            summary = "Desativar Filme",
            description = "Altera o status do filme para desativado"
    )
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

    @Operation(
            summary = "Ativar Filme",
            description = "Altera o status do filme para Disponivel"
    )
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
