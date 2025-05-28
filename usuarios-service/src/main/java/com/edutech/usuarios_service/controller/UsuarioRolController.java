package com.edutech.usuarios_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.usuarios_service.model.UsuarioRol;
import com.edutech.usuarios_service.service.UsuarioRolService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/usuarios-rol")
public class UsuarioRolController {

    @Autowired
    private UsuarioRolService usuarioRolService;

    @GetMapping()
    public ResponseEntity<List<UsuarioRol>> listar() {
        List<UsuarioRol> usuariosRol = usuarioRolService.findAll();
        if (usuariosRol.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuariosRol);
    }
    
    @PostMapping()
    public ResponseEntity<UsuarioRol> guardar(@RequestBody UsuarioRol usuarioRol) {
        UsuarioRol nuevoUsuarioRol = usuarioRolService.save(usuarioRol);
        return ResponseEntity.status(201).body(nuevoUsuarioRol);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRol> buscar(@PathVariable("id") UUID id) {
        try {
            UsuarioRol usuarioRol = usuarioRolService.findById(id);
            if (usuarioRol == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(usuarioRol);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRol> actualizar(@PathVariable("id") UUID id, @RequestBody UsuarioRol usuarioRol) {
        try {
            UsuarioRol usur = usuarioRolService.findById(id);
            usur.setId(id);
            usur.setUsuario(usuarioRol.getUsuario());
            usur.setRol(usuarioRol.getRol());

            usuarioRolService.save(usur);
            return ResponseEntity.ok(usur);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") UUID id) {
        try {
            usuarioRolService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
