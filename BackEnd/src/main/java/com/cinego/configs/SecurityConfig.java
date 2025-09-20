package com.cinego.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        //autorizações das rotas de usuario
                        .requestMatchers(HttpMethod.POST,"/access/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/usuario/listarTodos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/usuario/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/usuario/novo").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuario/editar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuario/excluir/**").hasRole("ADMIN")


                        //autorizações das rotas de filme
                        .requestMatchers(HttpMethod.GET,"/filme/listarFilmes").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/filme/buscarFilmeId/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/filme/cadastrarFilme").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/editarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/alugarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/devolverFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/desativarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/ativarFilme/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/filme/excluir/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
