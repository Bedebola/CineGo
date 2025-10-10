package com.cinego.application.services;

import com.cinego.domain.entities.Filme;
import com.cinego.domain.entities.RegistroLocacao;
import com.cinego.domain.entities.Usuario;
import com.cinego.domain.repositories.FilmeRepository;
import com.cinego.domain.repositories.RegistroLocacaoRepository;
import com.cinego.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistroLocacaoService {

    @Autowired
    private RegistroLocacaoRepository registroLocacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    public RegistroLocacao registrarLocacao(Long usuarioId, Long filmeId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        RegistroLocacao registro = new RegistroLocacao(
                usuario, filme, LocalDateTime.now()
        );
        registroLocacaoRepository.save(registro);
        return null;
    }

    public RegistroLocacao registrarDevolucao(Long filmeId) {
        RegistroLocacao registroExistente = registroLocacaoRepository.findByFilmeIdAndDataDevolucao(filmeId, null);

            registroExistente.setDataDevolucao(LocalDateTime.now());
            registroLocacaoRepository.save(registroExistente);

        return null;
    }
}
