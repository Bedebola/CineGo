package com.cinego.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.cinego.dtos.LoginRequest;
import com.cinego.models.Token;
import com.cinego.models.Usuario;
import com.cinego.repositories.TokenRepository;
import com.cinego.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.secretKey}")
    private String secret;

    @Value("${spring.tempo_expiracao}")
    private Long tempo;

    private String emissor = "CineGoByBedeBola";

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String gerarToken(LoginRequest request){

        var usuario = usuarioRepository.findByEmail(request.email()).orElse(null);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(usuario.getEmail())
                .withExpiresAt(this.gerarDataExpiracao())
                .sign(algorithm);

        tokenRepository.save(new Token(null, token, usuario));
        return token;

    }

    public Instant gerarDataExpiracao(){
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }

    public Usuario validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(emissor)
                .build();

        verifier.verify(token);

        var tokenResult = tokenRepository.findByToken(token).orElse(null);

        if (tokenResult == null){
            throw new IllegalArgumentException("Token invalido!");
        }

        return tokenResult.getUsuario();
    }

}
