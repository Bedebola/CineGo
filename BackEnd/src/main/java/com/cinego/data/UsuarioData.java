package com.cinego.data;

import com.cinego.models.Usuario;
import com.cinego.repositories.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioData {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void LocalUsuarioData(){

        if (usuarioRepository.count() == 0){

            List<Usuario> usuarios = List.of(
                    new Usuario("Hermione Granger", "111.111.111-11", "1234", "hermione@hogwarts.com.br", "ADMIN"),
                    new Usuario("Draco Malfoy", "222.222.222-22", "1234", "draco@hogwarts.com.br", "ADMIN"),
                    new Usuario("Ronald Weasley", "333.333.333-33", "1234", "ronald@hogwarts.com.br", "ADMIN"),
                    new Usuario("Harry Potter", "444.444.444-44", "1234", "harry@hogwarts.com.br", "ADMIN"),
                    new Usuario("Voldemort", "555.555.555-55", "1234", "voldemort@comensaldamorte.com.br", "ADMIN"),
                    new Usuario("Hagrid", "666.666.666-66", "1234", "hagrid@hogwarts.com.br", "ADMIN"),
                    new Usuario("Alvo Dumbledore", "777.777.777-77", "1234", "alvo@hogwarts.com.br", "ADMIN"),
                    new Usuario("Minerva McGonagall", "888.888.888-88", "1234", "minerva@hogwarts.com.br", "ADMIN"),
                    new Usuario("Severo Snape", "999.999.999-99", "1234", "severo@hogwarts.com.br", "ADMIN")
            );
            usuarioRepository.saveAll(usuarios);
        }
    }
}
