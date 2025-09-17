package com.cinego.services;

import com.cinego.enums.StatusFilme;
import com.cinego.exceptions.AcaoInvalidaException;
import com.cinego.exceptions.ArgumentoInvalidoOuNaoEncontradoException;
import com.cinego.models.Filme;
import com.cinego.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public List<Filme> listarFilmes() throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            List<Filme> listaFilmesRetorno = filmeRepository.findAll();

            if (listaFilmesRetorno.isEmpty()){
                throw new ArgumentoInvalidoOuNaoEncontradoException("A lista de filmes encontra-se vazia!");
            }

            return listaFilmesRetorno;

        } catch (Exception e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Ocorreu um erro ao buscar os filmes cadastrados: " + e.getMessage());
        }
    }

    public Filme buscarFilmePorId(Long filmeId) throws ArgumentoInvalidoOuNaoEncontradoException {

        try {
            Filme filme = filmeRepository.findById(filmeId)
                    .orElseThrow(()-> new ArgumentoInvalidoOuNaoEncontradoException("Nenhum filme foi encontrado para o ID informado!"));

            return filme;

        } catch (Exception e) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Ocorreu um erro ao buscar o filme pelo id: " + e.getMessage());
        }
    }

    public void validarCamposFilme (Filme filme,Long idIgnore) throws ArgumentoInvalidoOuNaoEncontradoException {

        if (filme == null){
            throw new ArgumentoInvalidoOuNaoEncontradoException("Filme não pode ser nulo!");
        }
        if (filme.getNome() == null || filme.getNome().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo NOME não pode ser vazio!");
        }
        if (filme.getSinopse() == null || filme.getSinopse().isEmpty()){
            throw new ArgumentoInvalidoOuNaoEncontradoException("O campo SINOPSE não pode ser vazio!");
        }

        boolean nomeExiste = (idIgnore == null)
                ? filmeRepository.existsByNomeIgnoreCase(filme.getNome())
                : filmeRepository.existsByNomeIgnoreCaseAndIdNot(filme.getNome(), idIgnore);

        if (nomeExiste) {
            throw new ArgumentoInvalidoOuNaoEncontradoException("Já existe um filme registrado com esse nome!");
        }
    }

    public Filme cadastrarFilme(Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException {
        validarCamposFilme(filme, null);
        filme.setStatus(StatusFilme.DISPONIVEL);

        return filmeRepository.save(filme);
    }

    public Filme editarFilme(Long idFilme, Filme filme) throws ArgumentoInvalidoOuNaoEncontradoException{
        Filme filmeExistente = buscarFilmePorId(idFilme);

        validarCamposFilme(filme, idFilme);

        filmeExistente.setNome(filme.getNome());
        filmeExistente.setSinopse(filme.getSinopse());

        return filmeRepository.save(filmeExistente);
    }

    public Filme alugarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = buscarFilmePorId(idFilme);

        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL){
            throw new AcaoInvalidaException("Não foi possível completar a ação: No momento, o título selecionado encontra-se alugado ou inativo.");
        }

        filmeRegistrado.setStatus(StatusFilme.ALUGADO);
        return filmeRepository.save(filmeRegistrado);
    }

    public Filme devolverFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = buscarFilmePorId(idFilme);

        if (filmeRegistrado.getStatus() != StatusFilme.ALUGADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está alugado, portanto não pode ser devolvido.");
        }

        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        return filmeRepository.save(filmeRegistrado);
    }

    public Filme desativarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = buscarFilmePorId(idFilme);

        if (filmeRegistrado.getStatus() != StatusFilme.DISPONIVEL){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado está atualmente alugado, portanto não pode ser desativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DESATIVADO);
        return filmeRepository.save(filmeRegistrado);
    }

    public Filme ativarFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = buscarFilmePorId(idFilme);

        if (filmeRegistrado.getStatus() != StatusFilme.DESATIVADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado não está desativado, portanto não pode ser ativado.");
        }

        filmeRegistrado.setStatus(StatusFilme.DISPONIVEL);
        return filmeRepository.save(filmeRegistrado);
    }

    public void excluirFilme(Long idFilme) throws ArgumentoInvalidoOuNaoEncontradoException {

        Filme filmeRegistrado = buscarFilmePorId(idFilme);

        if (filmeRegistrado.getStatus() == StatusFilme.ALUGADO){
            throw new AcaoInvalidaException("Não foi possível completar a ação: O título selecionado está atualmente alugado, faça a devolução para poder excluir.");
        }
        filmeRepository.delete(filmeRegistrado);
    }
}