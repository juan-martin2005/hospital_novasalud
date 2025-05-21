package com.hospital_novasalud.hospital_nova_salud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital_novasalud.hospital_nova_salud.models.Usuario;
import com.hospital_novasalud.hospital_nova_salud.repositories.IUsuarioRepository;


@Service
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Usuario> usuar = usuarioRepository.findByNombreUsua(username);
        if(usuar.isEmpty()){
            throw new UsernameNotFoundException(String.format("El usuario %s no existe", username));
        }

        Usuario usuario = usuar.orElseThrow();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().getNombreRol()));

        return new User(
            usuario.getNombreUsua(),
            usuario.getContrasena(),
            authorities
        );
    }

}
