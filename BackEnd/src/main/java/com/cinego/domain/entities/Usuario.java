package com.cinego.domain.entities;

import com.cinego.application.dtos.usuario.UsuarioRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
@Tag(name = "Entidade Usuario", description = "Entidade de usuarios do sistema.")
public class Usuario implements UserDetails {

    public Usuario(UsuarioRequestDTO usuarioRequest){
        this.setNome(usuarioRequest.nome());
        this.setCpf(usuarioRequest.cpf());
        this.setEmail(usuarioRequest.email());
        this.setRole(usuarioRequest.role());
        this.setSenha(usuarioRequest.senha());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String email;
    private String role;
    private String senha;

    public Usuario(String nome, String cpf, String senha, String email, String role) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if ("ADMIN".equals(this.role)) {
            return List.of(new SimpleGrantedAuthority("ADMIN"));
        }else {
            return List.of(new SimpleGrantedAuthority("USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}