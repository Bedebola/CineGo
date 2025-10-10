package com.cinego.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroLocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "filme_id")
    private Filme filme;

    private LocalDateTime dataLocacao;

    private LocalDateTime dataDevolucao;


    public RegistroLocacao(Usuario usuario, Filme filme, LocalDateTime dataLocacao) {
        this.usuario = usuario;
        this.filme = filme;
        this.dataLocacao = dataLocacao;
    }

    public RegistroLocacao(LocalDateTime dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

}
