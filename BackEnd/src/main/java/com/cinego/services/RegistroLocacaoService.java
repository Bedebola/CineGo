package com.cinego.services;

import com.cinego.models.Filme;
import com.cinego.models.RegistroLocacao;
import com.cinego.models.Usuario;
import com.cinego.repositories.FilmeRepository;
import com.cinego.repositories.RegistroLocacaoRepository;
import com.cinego.repositories.UsuarioRepository;
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
