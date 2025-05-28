package com.edutech.usuarios_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.service.RolService;

@RestController
@RequestMapping("/api/v1/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> getAllRols() {
        List<Rol> rols = rolService.findAll();
        if (rols.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rols);
    }
    
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        if (rol.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Rol savedRol = rolService.save(rol);
        return ResponseEntity.ok(savedRol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@RequestParam UUID id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable UUID id, @RequestBody Rol rol) {
        try {
            Rol rolcito = rolService.findById(id);
            rolcito.setId(id);
            rolcito.setNombre(rol.getNombre());
            rolcito.setDescripcion(rol.getDescripcion());
            rolcito.setPermisos(rol.getPermisos());
            rolcito.setUsuarios(rol.getUsuarios());
            rolcito.setFechaCreacion(rol.getFechaCreacion());
            rolcito.setEstaActivo(rol.isEstaActivo());

            rolService.save(rolcito);
            return ResponseEntity.ok(rolcito);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable UUID id) {
        try {
            rolService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
