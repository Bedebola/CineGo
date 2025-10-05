package com.cinego.repositories;

import com.cinego.models.RegistroLocacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RegistroLocacaoRepository extends JpaRepository<RegistroLocacao, Long> {

    RegistroLocacao findByFilmeIdAndDataDevolucao(Long filmeId, LocalDateTime dataDevolucao);
}
