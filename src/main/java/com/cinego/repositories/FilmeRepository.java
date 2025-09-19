package com.cinego.repositories;

import com.cinego.models.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    boolean existsByTituloIgnoreCase(String titulo);
    boolean existsByTituloIgnoreCaseAndIdNot(String titulo, Long id);
}
