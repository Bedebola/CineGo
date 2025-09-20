package com.cinego.services;

import com.cinego.dtos.DtoFilme;
import com.cinego.enums.StatusFilme;
import com.cinego.exceptions.AcaoInvalidaException;
import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Filme;
import com.cinego.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public DtoFilme criarRetornoFilme(Filme filme)throws ArgumentoInvalidoOuNaoEncontradoException{
        DtoFilme filmeRetorno = new DtoFilme();
        filmeRetorno.setTitulo(filme.getTitulo());
        filmeRetorno.setSinopse(filme.getSinopse());
        filmeRetorno.setStatus(filme.getStatus());

        return filmeRetorno;
    }

    public List<DtoFilme> listarFilmes() throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            List<Filme> listaFilmes = filmeRepository.findAll();

            if (listaFilmes.isEmpty()){
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de filmes encontra-se vazia!");
            }

            List<DtoFilme> listaFilmesRetorno = new ArrayList<>();

            for (int i = 0; i < listaFilmes.size(); i++) {
                Filme filme = listaFilmes.get(i);
                DtoFilme dtoFilme = criarRetornoFilme(filme);

                listaFilmesRetorno.add(dtoFilme);
            }

            return listaFilmesRetorno;

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao buscar os filmes cadastrados: " + e.getMessage());
        }
    }

    public DtoFilme buscarFilmePorId(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            Filme filme = filmeRepository.findById(filmeId)
                    .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme foi encontrado para o ID informado!"));

            return criarRetornoFilme(filme);

        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao buscar o filme pelo id: " + e.getMessage());
        }
    }

    public void validarCamposFilme (Filme filme,Long idIgnore) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (filme == null){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Filme não pode ser nulo!");
        }
        if (filme.getTitulo() == null || filme.getTitulo().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio!");
        }
        if (filme.getSinopse() == null || filme.getSinopse().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SINOPSE não pode ser vazio!");
        }

        boolean nomeExiste = (idIgnore == null)
                ? filmeRepository.existsByTituloIgnoreCase(filme.getTitulo())
                : filmeRepository.existsByTituloIgnoreCaseAndIdNot(filme.getTitulo(), idIgnore);

        if (nomeExiste) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um filme registrado com esse nome!");
        }
    }

    public DtoFilme cadastrarFilme(Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException {
        try {
            validarCamposFilme(filme, null);
            filme.setStatus(StatusFilme.DISPONIVEL);
            filmeRepository.save(filme);

            return criarRetornoFilme(filme);
        } catch (Exception e) {
            throw new AcaoInvalidaException("Ocorreu um erro ao cadastrar o filme: " + e.getMessage());
        }
    }

    public DtoFilme editarFilme(Long idFilme, Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException{

        try {
            Filme filmeExistente = filmeRepository.findById(idFilme)
                    .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

            validarCamposFilme(filmeExistente, idFilme);
            filmeExistente.setTitulo(filme.getTitulo());
            filmeExistente.setSinopse(filme.getSinopse());

            return criarRetornoFilme(filmeExistente);

        } catch (ArgumentoInvalidoOuNaoEncontradoException e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Não foi possível concluir a ação: "+e);
        }
    }

    public DtoFilme alugarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(idFilme)
                .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL){
            throw new AcaoInvalidaException("Não foi possível completar a ação: No momento, o título selecionado encontra-se alugado ou inativo.");
        }

        filmeRegistrado.setStatus(StatusFilme.ALUGADO);
        return criarRetornoFilme(filmeRegistrado);
    }

    public DtoFilme devolverFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(idFilme)
                .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));


        if (filmeRegistrado.getStatus() != StatusFilme.ALUGADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está alugado, portanto não pode ser devolvido.");
        }

        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public DtoFilme desativarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(idFilme)
                .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));


        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado está atualmente alugado, portanto não pode ser desativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DESATIVADO);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public DtoFilme ativarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(idFilme)
                .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

        if (filmeRegistrado.getStatus() != StatusFilme.DESATIVADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está desativado, portanto não pode ser ativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        filmeRepository.save(filmeRegistrado);
        return criarRetornoFilme(filmeRegistrado);
    }

    public void excluirFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = filmeRepository.findById(idFilme)
                .orElseThrow(()->new ArgumentoInvalidoOuNaoEncontradoException("Nenhum usuario encontrado para o id informado"));

        if (filmeRegistrado.getStatus() == StatusFilme.ALUGADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado está atualmente alugado, faça a devolução para poder excluir.");
        }
        filmeRepository.delete(filmeRegistrado);
    }
}