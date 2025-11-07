package com.cinego.application.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cinego.application.dtos.usuario.UsuarioPrincipalDTO;
import com.cinego.application.dtos.login.LoginRequest;
import com.cinego.domain.entities.Token;
import com.cinego.domain.repositories.TokenRepository;
import com.cinego.domain.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Tag(name = "TokenService", description = "Classe de serviço da entidade TOKEN onde o token é gerado e validado.")
@Service
public class TokenService {

    @Value("${spring.secret}")
    private String secret;

    @Value("${spring.expirationTime}")
    private Long tempo;

    private String emissor = "cinegoapp";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String gerarToken(LoginRequest request){

        var usuario = usuarioRepository.findByEmail(request.email()).orElse(null);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> roles = usuario.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                .withClaim("roles", roles)
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        Token novoToken = new Token(null, token, usuario);
        tokenRepository.save(novoToken);

        return token;
    }

    public Instant gerarDataExpiracao(){
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }

    public UsuarioPrincipalDTO validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(emissor)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        List<String> roles = jwt.getClaim("roles").asList(String.class);

        verifier.verify(token);

        var tokenResult = tokenRepository.findByToken(token).orElse(null);

        if (tokenResult == null){
            throw new IllegalArgumentException("Token invalido!");
        }

        return new UsuarioPrincipalDTO(tokenResult.getUsuario());
    }

}
