package com.cinego.infra.configs;

import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "SecurityConfig", description = "Classe de configuração das rotas da API")
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

                        //.requestMatchers("/**").permitAll()

                        //autorizações do swagger
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()

                        //autorizações das rotas de autenticação
                        .requestMatchers(HttpMethod.POST,"/access/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/access/recuperarsenha/envio").permitAll()

                        //autorizações das rotas de usuario
                        .requestMatchers(HttpMethod.POST,"/usuario/criarAdminInicial").permitAll()
                        .requestMatchers(HttpMethod.GET,"/usuario/listarUsuarios").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/usuario/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/usuario/cadastrarUsuario").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuario/editarUsuario/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuario/excluirUsuario/**").hasAuthority("ADMIN")

                        //autorizações das rotas de filme
                        .requestMatchers(HttpMethod.GET,"/filme/listarFilmes").authenticated()
                        .requestMatchers(HttpMethod.GET,"/filme/listarFilmesPorStatus/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/filme/enviarEmailLembreteDeDevolucao/**").authenticated()
                        .requestMatchers(HttpMethod.GET,"/filme/buscarFilmeId/**").authenticated()
                        .requestMatchers(HttpMethod.POST,"/filme/cadastrarFilme").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/editarFilme/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/alugarFilme/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/filme/devolverFilme/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/filme/desativarFilme/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/filme/ativarFilme/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/filme/excluirFilme/**").hasAuthority("ADMIN")
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
