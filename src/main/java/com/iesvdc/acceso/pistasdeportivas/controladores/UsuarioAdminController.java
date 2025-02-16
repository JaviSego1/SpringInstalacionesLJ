package com.iesvdc.acceso.pistasdeportivas.controladores;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/usuario")
public class UsuarioAdminController {

    @Autowired
    private ServiUsuario usuarioService;

    @GetMapping
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findOne(@PathVariable @NonNull Long id) {
        Optional<Usuario> oUsuario = usuarioService.findById(id);
        return oUsuario.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) throws URISyntaxException {
        Usuario created = usuarioService.save(usuario);
        return ResponseEntity.created(new URI("/api/admin/usuario/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updated = usuarioService.update(id, usuario);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        Optional<Usuario> oUsuario = usuarioService.findById(id);
        if (oUsuario.isPresent()) {
            usuarioService.delete(oUsuario.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
