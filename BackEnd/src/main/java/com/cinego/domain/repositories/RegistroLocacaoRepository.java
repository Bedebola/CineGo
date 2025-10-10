package com.cinego.domain.repositories;

import com.cinego.domain.entities.RegistroLocacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RegistroLocacaoRepository extends JpaRepository<RegistroLocacao, Long> {

    RegistroLocacao findByFilmeIdAndDataDevolucao(Long filmeId, LocalDateTime dataDevolucao);
}
