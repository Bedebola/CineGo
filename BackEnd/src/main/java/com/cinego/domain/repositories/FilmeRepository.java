package com.cinego.domain.repositories;

import com.cinego.domain.enums.StatusFilme;
import com.cinego.domain.entities.Filme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Tag(name = "Repositorio de Filmes", description = "Repositorio da entide Filme.")
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);

    List<Filme> findByStatus(StatusFilme status);

//    @Query("")
//    List<Filme> findByStatusAndUsuarioId(StatusFilme statusFilme, Long usuarioId);
}
