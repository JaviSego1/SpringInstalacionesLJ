package com.iesvdc.acceso.pistasdeportivas.servicios;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iesvdc.acceso.pistasdeportivas.modelos.Instalacion;
import com.iesvdc.acceso.pistasdeportivas.repos.RepoInstalacion;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





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
    public ResponseEntity<Instalacion> findOne(
        @PathVariable @NonNull Long id) {
        
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            return ResponseEntity.ok().body(oInstalacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/instalacion/{id}")
    public ResponseEntity<Instalacion> deleteOne(
        @PathVariable @NonNull Long id) {
        
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            repoInstalacion.delete(oInstalacion.get());
            return ResponseEntity.ok().body(oInstalacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/instalacion")
    public ResponseEntity<Instalacion> create(@RequestBody Instalacion instalacion) 
        throws URISyntaxException {

        Instalacion i = repoInstalacion.save(instalacion);
        return ResponseEntity.
            created(new URI("/api/instalacion/"+i.getId())).
            body(i);
    }
    
    @PutMapping("/instalacion/{id}")
    public ResponseEntity<Instalacion> putInstalacion
        (@PathVariable Long id, @RequestBody Instalacion instalacion) {
        
        Optional<Instalacion> oInstalacion = repoInstalacion.findById(id);
        if (oInstalacion.isPresent()) {
            Instalacion oldInstalacion = oInstalacion.get();
            oldInstalacion.setNombre(instalacion.getNombre());
            repoInstalacion.save(oldInstalacion);            
            return ResponseEntity.ok().body(oInstalacion.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    
}
