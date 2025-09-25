package com.cinego.models;

import com.cinego.enums.StatusFilme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Entidade Filme", description = "Entidade central do programa.")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String sinopse;
    private StatusFilme status;

    public Filme(String titulo, String sinopse, StatusFilme status) {
        this.sinopse = sinopse;
        this.status = status;
        this.titulo = titulo;
    }
}
