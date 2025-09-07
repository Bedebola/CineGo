package com.cinego.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        //autorizações do swagger
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()

                        //autorizações das rotas de usuario
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/listarTodos").hasRole("ADMIN")
                        .requestMatchers("/usuario/**").authenticated()
                        .requestMatchers("/novo").hasRole("ADMIN")

                        //autorizações das rotas de filme
                        .requestMatchers("/listarFilmes").authenticated()
                        .requestMatchers("/buscarFilmeId/**").authenticated()
                        .requestMatchers("/cadastrarFilme").hasRole("ADMIN")
                        .requestMatchers("/editarFilme/**").hasRole("ADMIN")
                        .requestMatchers("/alugarFilme/**").hasRole("ADMIN")
                        .requestMatchers("/devolverFilme/**").hasRole("ADMIN")
                        .requestMatchers("/desativarFilme/**").hasRole("ADMIN")
                        .requestMatchers("/ativarFilme/**").hasRole("ADMIN")
                        .requestMatchers("/excluir/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
