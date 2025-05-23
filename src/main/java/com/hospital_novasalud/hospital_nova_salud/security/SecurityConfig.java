package com.hospital_novasalud.hospital_nova_salud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Configuracion de quienes pueden acceder a los endpoints
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/usuarios/**").hasAuthority("ROL_ADMIN")
            .requestMatchers("/api/doctor/**").hasAuthority("ROL_ADMIN")
            .requestMatchers("/api/recepcionista/**").hasAuthority("ROL_ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/paciente/eliminar/{id}").hasAuthority("ROL_ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/paciente/listar").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/paciente/registrar").anonymous()
            //Aun no realizadas
            .requestMatchers(HttpMethod.GET, "/api/cita-medica/listar").hasAuthority("ROL_PACIENTE")
            .requestMatchers(HttpMethod.POST, "/api/cita-medica/registrar").hasAuthority("ROL_PACIENTE")
            .requestMatchers(HttpMethod.PUT, "/api/cita-medica/modificar/{id}").hasAuthority("ROL_PACIENTE")
            .requestMatchers(HttpMethod.DELETE, "/api/cita-medica/eliminar/{id}").hasAuthority("ROL_PACIENTE")
            .anyRequest().authenticated())
            .addFilter(new JwtAtuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .csrf(config -> config.disable())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
}
