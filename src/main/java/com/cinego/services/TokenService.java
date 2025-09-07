package com.cinego.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.cinego.configs.JwtFilter;
import com.cinego.models.Token;
import com.cinego.repositories.TokenRepository;
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

    private String emissor = "cinegobybedebola";

    @Autowired
    private TokenRepository tokenRepository;

    public String gerarToken(String email, String senha){

        Algorithm algorithm = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withIssuer(emissor)
                .withSubject(email)
                .withExpiresAt(this.gerarDataExpiracao() )
                .sign(algorithm);

        tokenRepository.save(new Token(null, token, email));
        return token;
    }

    public Instant gerarDataExpiracao(){
        var dataAtual = LocalDateTime.now();
        dataAtual = dataAtual.plusMinutes(tempo);

        return dataAtual.toInstant(ZoneOffset.of("-03:00"));
    }


    public String validarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verificador = JWT.require(algorithm)
                .withIssuer(emissor)
                .build();

        verificador.verify(token);

        var tokenResult = tokenRepository.findByToken(token).orElse(null);

        if (tokenResult == null){
            throw new IllegalArgumentException("O token não é válido!");
        }

        return tokenResult.getUsuario();
    }
}
