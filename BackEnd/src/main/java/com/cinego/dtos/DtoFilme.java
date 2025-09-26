package com.cinego.dtos;

import com.cinego.enums.StatusFilme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "DTO Filme", description = "DTO desitnado a transição de dados da entidade filme entre requests e responses")
public class DtoFilme {

    private Long filmeId;
    private String titulo;
    private String genero;
    private String sinopse;
    private StatusFilme status;
}
