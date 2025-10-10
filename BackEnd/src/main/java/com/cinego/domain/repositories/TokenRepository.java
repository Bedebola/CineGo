package com.cinego.domain.repositories;

import com.cinego.domain.entities.Token;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Tag(name = "Repositorio de Tokens", description = "Repositorio da entide Token.")
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

}
