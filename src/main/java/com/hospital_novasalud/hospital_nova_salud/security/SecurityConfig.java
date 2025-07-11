package com.hospital_novasalud.hospital_nova_salud.security;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            .requestMatchers(HttpMethod.POST, "/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/doctor/agregar-horario").hasAuthority("ROL_DOCTOR")
            .requestMatchers(HttpMethod.POST, "/api/paciente/registrar").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/paciente/cita/{id}/boleta").hasAuthority("ROL_PACIENTE")
            .requestMatchers(HttpMethod.POST, "/api/paciente/registrar-cita-con-id").hasAuthority("ROL_PACIENTE")
            .requestMatchers(HttpMethod.GET, "/api/doctor/listar-horario").hasAnyAuthority("ROL_DOCTOR")
            .requestMatchers(HttpMethod.GET, "/api/doctor/listar-citas").hasAnyAuthority("ROL_DOCTOR")
            .requestMatchers(HttpMethod.GET, "/api/especialidades/listar").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/doctor/listar").permitAll()
            .requestMatchers("api/doctor/cita/gestion/**").hasAuthority("ROL_DOCTOR")
            .requestMatchers(HttpMethod.GET, "/api/paciente/listar").hasAnyAuthority( "ROL_DOCTOR", "ROL_RECEPCIONISTA")
            .requestMatchers("/api/recepcionista/medicamento/**").hasAnyAuthority("ROL_RECEPCIONISTA","ROL_DOCTOR")
            .requestMatchers(HttpMethod.GET, "/api/doctor/listar-horario/{doctorId}").permitAll()
            .requestMatchers("/api/paciente/**").hasAuthority("ROL_PACIENTE")
            //Añadí los endpoints faltantes

            .requestMatchers("/api/doctor/**").hasAuthority("ROL_ADMIN")
            .requestMatchers("/api/usuarios/**").hasAuthority("ROL_ADMIN")
            .requestMatchers("/api/recepcionista/**").hasAuthority("ROL_ADMIN")
            .requestMatchers("/api/especialidades/**").hasAuthority("ROL_ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/paciente/eliminar/{id}").hasAuthority("ROL_ADMIN")
            .anyRequest().authenticated())
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtValidationFilter(authenticationManager()))
            .csrf(config -> config.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://mango-flower-0e8720c0f.6.azurestaticapps.net"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;

    }
}
