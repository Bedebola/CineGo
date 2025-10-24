package com.cinego.infra.external;

import com.cinego.domain.interfaces.IEnvioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EnvioEmailRepository implements IEnvioEmail {

    @Autowired
    private JavaMailSender javaMailSender;


    @Async
    public void enviarEmailSImples(String para, String assunto, String texto) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("nao-responda@cinego.com");
            message.setTo(para);
            message.setSubject(assunto);
            message.setText(texto);
            javaMailSender.send(message);

        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void enivarEmailComTemplate(String para, String assunto, String texto) {

    }
}
