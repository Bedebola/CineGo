package com.cinego.domain.repositories;

import com.cinego.domain.entities.RegistroLocacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroLocacaoRepository extends JpaRepository<RegistroLocacao, Long> {

    RegistroLocacao findByFilmeIdAndIsDevolvido(Long filmeId, boolean isDevolvido);
}
