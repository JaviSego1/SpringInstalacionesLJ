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
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioAdminController {

    @Autowired
    private ServiUsuario serviUsuario;

    // Devuelve todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(serviUsuario.findAll());
    }
    // Devuelve un usuario por su id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable @NonNull Long id) {
        Optional<Usuario> usuario = serviUsuario.findById(id);
        return usuario.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    // Crea un usuario
    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario usuario) throws URISyntaxException {
        Usuario created = serviUsuario.save(usuario);
        return ResponseEntity.created(new URI(""+created.getId()))
                    .body(created);
    }

    // Actualiza un usuario por su id
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updated = serviUsuario.update(id, usuario);
        return updated.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    // Borra un usuario por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<Usuario> oUsuario = serviUsuario.findById(id);
        if (oUsuario.isPresent()) {
            serviUsuario.delete(oUsuario.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
