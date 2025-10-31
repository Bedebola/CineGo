package com.cinego.application.services;

import com.cinego.domain.entities.Filme;
import com.cinego.domain.entities.RegistroLocacao;
import com.cinego.domain.entities.Usuario;
import com.cinego.domain.interfaces.IEnvioEmail;
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

    @Autowired
    private IEnvioEmail iEnvioEmail;

    public RegistroLocacao registrarLocacao(Long usuarioId, Long filmeId, String emailCliente) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        LocalDateTime agora = LocalDateTime.now();

        RegistroLocacao registro = new RegistroLocacao(
                usuario,
                filme,
                agora,
                agora.plusDays(3L)
        );

        registro.setEmailCliente(emailCliente);

        registroLocacaoRepository.save(registro);

        return registro;
    }

    public RegistroLocacao enviarEmailDevolucao(RegistroLocacao registroLocacao) {

        if (registroLocacao.getDataDevolucao().equals(LocalDateTime.now())){
            iEnvioEmail.enviarEmailSImples(
                    registroLocacao.getEmailCliente(),
                    "Lembrete de Devolução",
                    "CineGo lembra que você precisa devolver o filme 'A volta dos que não foram' hoje!"
            );
        }

        return null;
    }
}
