package com.cinego.application.services;

import com.cinego.application.dtos.filme.FilmeRequestDTO;
import com.cinego.domain.enums.StatusFilme;
import com.cinego.domain.exceptions.AcaoInvalidaException;
import com.cinego.domain.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.domain.entities.Filme;
import com.cinego.domain.repositories.FilmeRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "FilmeService", description = "Classe de serviço da entidade FILME onde são implementadas as regras de negocio e validação de campos.")
@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private RegistroLocacaoService registroLocacaoService;

    public FilmeRequestDTO criarRetornoFilme(Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException {
        FilmeRequestDTO filmeRetorno = new FilmeRequestDTO();
        filmeRetorno.setFilmeId(filme.getId());
        filmeRetorno.setTitulo(filme.getTitulo());
        filmeRetorno.setGenero(filme.getGenero());
        filmeRetorno.setSinopse(filme.getSinopse());
        filmeRetorno.setStatus(filme.getStatus());

        return filmeRetorno;
    }

    public List<FilmeRequestDTO> listarFilmes() throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            List<Filme> listaFilmes = filmeRepository.findAll();

            if (listaFilmes.isEmpty()) {
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de filmes encontra-se vazia!");
            }

            List<FilmeRequestDTO> listaFilmesRetorno = new ArrayList<>();

            for (int i = 0; i < listaFilmes.size(); i++) {
                Filme filme = listaFilmes.get(i);
                FilmeRequestDTO filmeRequestDTO = criarRetornoFilme(filme);

                listaFilmesRetorno.add(filmeRequestDTO);
            }

            return listaFilmesRetorno;

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao buscar os filmes cadastrados: " + e.getMessage());
        }
    }

    public List<FilmeRequestDTO> listarFilmesPorStatus(StatusFilme status) throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            List<Filme> listaFilmes = filmeRepository.findByStatus(status);

            if (listaFilmes.isEmpty()) {
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de filmes encontra-se vazia!");
            }

            List<FilmeRequestDTO> listarFilmesPorStatusRetorno = new ArrayList<>();

            for (int i = 0; i < listaFilmes.size(); i++) {
                Filme filme = listaFilmes.get(i);
                FilmeRequestDTO filmeRequestDTO = criarRetornoFilme(filme);

                listarFilmesPorStatusRetorno.add(filmeRequestDTO);
            }

            return listarFilmesPorStatusRetorno;

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao buscar os filmes cadastrados: " + e.getMessage());
        }
    }

    public Filme buscarFilmePorId(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            Filme filme = filmeRepository.findById(filmeId)
                    .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme foi encontrado para o ID informado!"));

            return filme;

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao buscar o filme pelo id: " + e.getMessage());
        }
    }

    public void validarCamposFilme(Filme filme, Long idIgnore) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (filme == null) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Filme não pode ser nulo!");
        }
        if (filme.getTitulo() == null || filme.getTitulo().isEmpty()) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio!");
        }
        if (filme.getGenero() == null || filme.getGenero().isEmpty()) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo GENERO não pode ser vazio!");
        }
        if (filme.getSinopse() == null || filme.getSinopse().isEmpty()) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SINOPSE não pode ser vazio!");
        }

        boolean nomeExiste = (idIgnore == null)
                ? filmeRepository.existsByTituloIgnoreCase(filme.getTitulo())
                : filmeRepository.existsByTituloIgnoreCaseAndIdNot(filme.getTitulo(), idIgnore);

        if (nomeExiste) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um filme registrado com esse nome!");
        }
    }

    public FilmeRequestDTO cadastrarFilme(Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException {
        try {
            validarCamposFilme(filme, null);
            filme.setStatus(StatusFilme.DISPONIVEL);

            filmeRepository.save(filme);

            return criarRetornoFilme(filme);
        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao cadastrar o filme: " + e.getMessage());
        }
    }

    public FilmeRequestDTO editarFilme(Long filmeId, Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            Filme filmeExistente = filmeRepository.findById(filmeId)
                    .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));

            validarCamposFilme(filmeExistente, filmeId);
            filmeExistente.setTitulo(filme.getTitulo());
            filmeExistente.setGenero(filme.getGenero());
            filmeExistente.setSinopse(filme.getSinopse());

            filmeRepository.save(filmeExistente);
            return criarRetornoFilme(filmeExistente);

        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Não foi possível concluir a ação: " + e);
        }
    }

    public FilmeRequestDTO alugarFilme(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));

        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL) {
            throw new AcaoInvalidaException("Não foi possível completar a ação: No momento, o título selecionado encontra-se alugado ou inativo.");
        }

        registroLocacaoService.registrarLocacao(1L, filmeRegistrado.getId());
        filmeRegistrado.setStatus(StatusFilme.ALUGADO);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public FilmeRequestDTO devolverFilme(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));


        if (filmeRegistrado.getStatus() != StatusFilme.ALUGADO)
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está alugado, portanto não pode ser devolvido.");

        registroLocacaoService.registrarDevolucao(filmeRegistrado.getId());
        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public FilmeRequestDTO desativarFilme(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));


        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL) {
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado está atualmente alugado, portanto não pode ser desativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DESATIVADO);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public FilmeRequestDTO ativarFilme(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));

        if (filmeRegistrado.getStatus() != StatusFilme.DESATIVADO) {
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está desativado, portanto não pode ser ativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public void excluirFilme(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme encontrado para o id informado"));

        filmeRepository.delete(filmeRegistrado);
    }
}