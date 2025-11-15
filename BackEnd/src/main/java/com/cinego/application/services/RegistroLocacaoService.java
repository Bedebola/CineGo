package com.cinego.application.services;

import com.cinego.application.dtos.usuario.UsuarioPrincipalDTO;
import com.cinego.domain.entities.Filme;
import com.cinego.domain.entities.RegistroLocacao;
import com.cinego.domain.entities.Usuario;
import com.cinego.domain.enums.StatusFilme;
import com.cinego.domain.interfaces.IEnvioEmail;
import com.cinego.domain.repositories.FilmeRepository;
import com.cinego.domain.repositories.RegistroLocacaoRepository;
import com.cinego.domain.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public RegistroLocacao registrarLocacao(UsuarioPrincipalDTO usuarioLogado, Filme filme, String emailCliente) {

        Filme filmeRegistrado = filmeRepository.findById((filme.getId()))
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioLogado.id())
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        LocalDateTime agora = LocalDateTime.now();

        RegistroLocacao registro = new RegistroLocacao(
                usuario,
                filmeRegistrado,
                agora,
                agora.plusDays(3L),
                emailCliente,
                false
        );

        registro.setEmailCliente(emailCliente);
        registroLocacaoRepository.save(registro);

        return registro;
    }

    public RegistroLocacao enviarEmailDevolucao(Long filmeId) {

        RegistroLocacao registroLocacao = registroLocacaoRepository.findByFilmeIdAndIsDevolvido(filmeId, false);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = registroLocacao.getDataDevolucao().format(formatter);

        // if (registroLocacao.getDataDevolucao().equals(LocalDateTime.now()) && registroLocacao.getFilme().getStatus() == StatusFilme.ALUGADO ){
            iEnvioEmail.enviarEmailSImples(
                    registroLocacao.getEmailCliente(),
                    "Lembrete de Devolução",
                    "CineGo lembra que você precisa devolver o filme:"+ registroLocacao.getFilme().getTitulo() + "até o dia:" + dataFormatada + "!"
            );
       // }
        return null;
    }
}
