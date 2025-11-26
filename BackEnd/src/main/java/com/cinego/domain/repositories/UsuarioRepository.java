package com.cinego.domain.repositories;

import com.cinego.domain.entities.Usuario;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Tag(name = "Repositorio de usuarios", description = "Repositorio da entide Usuario.")
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndNome(String email, String nome);
    boolean existsUsuarioByEmailContainingAndSenha(String email, String senha);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String nome);
    boolean existsByEmailIgnoreCaseAndIdNot(String nome, Long id);

    Optional<Usuario> findByCpf(String cpf);

}
