package com.senac.cadastro_clientes.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/clientes/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/clientes/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/clientes/listar").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/clientes/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"clientes/excluir/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/enderecos/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/enderecos/listar").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/enderecos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"enderecos/excluir/{id}").authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}