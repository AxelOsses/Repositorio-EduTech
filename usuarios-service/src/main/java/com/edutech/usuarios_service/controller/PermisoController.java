package com.edutech.usuarios_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.service.PermisoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1/permiso")
public class PermisoController {
    
    @Autowired
    private PermisoService permisoService;

    @GetMapping()
    public ResponseEntity<List<Permiso>> getAllPermisos() {
        List<Permiso> permisos = permisoService.findAll();
        if (permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(permisos);
    }
    
    @PostMapping()
    public ResponseEntity<Permiso> createPermiso(@RequestBody Permiso permiso) {
        if (permiso.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Permiso savedPermiso = permisoService.save(permiso);
        return ResponseEntity.ok(savedPermiso);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permiso> getPermisoById(@RequestParam UUID id) {
        Permiso permiso = permisoService.findById(id);
        if (permiso == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(permiso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Permiso> updatePermiso(@PathVariable UUID id, @RequestBody Permiso permiso) {
        try {
            Permiso per = permisoService.findById(id);
            per.setId(id);
            per.setNombre(permiso.getNombre());
            per.setDescripcion(permiso.getDescripcion());

            permisoService.save(per);
            return ResponseEntity.ok(per);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermiso(@PathVariable UUID id) {
        try {
            permisoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
