package com.cinego.domain.interfaces;

public interface IEnvioEmail {

    void enviarEmailSImples(String para, String assunto, String texto);

    void enivarEmailComTemplate(String para, String assunto, String texto);


}
