package com.iesvdc.acceso.pistasdeportivas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoUsuario;

public class ServiLoggedUser {
    
    @Autowired
    private RepoUsuario repoUsuario;

    public Usuario getLoggedUser(){
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();
        return repoUsuario.findByUsername(authentication.getName()).get(0);
    }
}
