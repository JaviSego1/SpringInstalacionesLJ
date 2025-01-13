package com.iesvdc.acceso.pistasdeportivas.servicios;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoInstalacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping(path = "/api")
public class ServiInstalacion {

    @Autowired
    RepoInstalacion repoInstalacion;

    @GetMapping("/instalacion")
    public List<Instalacion> findAll() {
        return repoInstalacion.findAll();
    }

    @GetMapping("/instalacion/{id}")
    public Instalacion findOne(
        @PathVariable @NonNull Long id) {
        
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            return oInstalacion.get();
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Instalacion no encontrada");
           
        }
    }
    
    @DeleteMapping("/instalacion/{id}")
    public Instalacion deleteOne(
        @PathVariable @NonNull Long id) {
        
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            repoInstalacion.delete(oInstalacion.get());
            return oInstalacion.get();
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Instalacion no encontrada");
           
        }
    }

    @PostMapping("/instalacion")
    public Instalacion create(@RequestBody Instalacion instalacion) {
        return repoInstalacion.save(instalacion);
    }
    
    
    
}
