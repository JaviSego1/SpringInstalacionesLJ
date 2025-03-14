package com.iesvdc.acceso.pistasdeportivas.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

@Service
public class ServiUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario getLoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return repoUsuario.findByUsername(authentication.getName()).get(0);
    }

    public List<Usuario> findAll(){
        return repoUsuario.findAll();
    }

    public Optional<Usuario> findById(Long id){
        return repoUsuario.findById(id);
    }

    public Usuario save(Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repoUsuario.save(usuario);
    }

    public void delete(Usuario usuario){
        repoUsuario.delete(usuario);
    }

    public Optional<Usuario> update(Long id, Usuario usuario) {

        return repoUsuario.findById(id).map(existing -> {

            existing.setUsername(usuario.getUsername());
            existing.setEmail(usuario.getEmail());
            existing.setEnabled(usuario.isEnabled());


            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

            existing.setTipo(usuario.getTipo());

            return repoUsuario.save(existing);
        });
    }

    public boolean existsById(Long id){
        return repoUsuario.existsById(id);
    }

    public void deleteById(Long id){
        repoUsuario.deleteById(id);
    }

    public boolean existsByUsername(String username){
        return repoUsuario.findByUsername(username).size() > 0;
    }
    
    public boolean existsByEmail(String email){
        return repoUsuario.findByEmail(email).size() > 0;
    }
}