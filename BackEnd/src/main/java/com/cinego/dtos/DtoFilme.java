package com.cinego.dtos;

import com.cinego.enums.StatusFilme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoFilme {

    private Long filmeId;
    private String titulo;
    private String sinopse;
    private StatusFilme status;
}
