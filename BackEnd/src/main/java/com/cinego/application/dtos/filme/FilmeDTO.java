package com.cinego.application.dtos.filme;

import com.cinego.domain.enums.StatusFilme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "DTO Filme", description = "DTO desitnado a transição de dados da entidade filme entre requests e responses")
public class FilmeDTO {
    private Long filmeId;
    private String titulo;
    private String genero;
    private String sinopse;
    private StatusFilme status;
}
