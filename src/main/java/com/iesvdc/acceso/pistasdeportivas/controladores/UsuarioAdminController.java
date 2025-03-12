package com.iesvdc.acceso.pistasdeportivas.controladores;

import com.iesvdc.acceso.pistasdeportivas.modelos.Usuario;
import com.iesvdc.acceso.pistasdeportivas.servicios.ServiUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/usuario")
public class UsuarioAdminController {

    @Autowired
    private ServiUsuario usuariosService;

    @GetMapping
    public List<Usuario> findAll() {
        return usuariosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuariosService.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        if (usuariosService.existsByUsername(usuario.getUsername())) {
            return ResponseEntity.badRequest().body(null); 
        }
        if (usuariosService.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body(null); 
        }
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(null); 
        }
        Usuario nuevoUsuario = usuariosService.save(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuariosService.update(id, usuario)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (!usuariosService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usuariosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}