package com.edutech.usuarios_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/permisos")
@Tag(name = "Permisos", description = "API para gestión de permisos")
public class PermisoController {
    
    @Autowired
    private PermisoService permisoService;

    @GetMapping()
    @Operation(summary = "Listar todos los permisos", description = "Obtiene una lista de todos los permisos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de permisos obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Permiso.class))),
        @ApiResponse(responseCode = "204", description = "No hay permisos registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Permiso>>> getAllPermisos() {
        List<Permiso> permisos = permisoService.findAll();
        if (permisos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<Permiso>> permisosConLinks = permisos.stream()
            .map(permiso -> {
                EntityModel<Permiso> entityModel = EntityModel.of(permiso);
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PermisoController.class).getPermisoById(permiso.getId())).withSelfRel());
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PermisoController.class).getAllPermisos()).withRel("permisos"));
                return entityModel;
            })
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Permiso>> collectionModel = CollectionModel.of(permisosConLinks);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).getAllPermisos()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @PostMapping()
    @Operation(summary = "Crear un nuevo permiso", description = "Registra un nuevo permiso en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Permiso creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Permiso.class))),
        @ApiResponse(responseCode = "400", description = "Datos de permiso inválidos")
    })
    public ResponseEntity<EntityModel<Permiso>> createPermiso(
            @Parameter(description = "Datos del permiso a crear", required = true)
            @RequestBody Permiso permiso) {
        if (permiso.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Permiso savedPermiso = permisoService.save(permiso);
        
        EntityModel<Permiso> entityModel = EntityModel.of(savedPermiso);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).getPermisoById(savedPermiso.getId())).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).getAllPermisos()).withRel("permisos"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar permiso por ID", description = "Obtiene un permiso específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Permiso.class))),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<EntityModel<Permiso>> getPermisoById(
            @Parameter(description = "ID del permiso a buscar", required = true)
            @PathVariable("id") Long id) {
        Permiso permiso = permisoService.findById(id);
        if (permiso == null) {
            return ResponseEntity.notFound().build();
        }
        
        EntityModel<Permiso> entityModel = EntityModel.of(permiso);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).getPermisoById(id)).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).getAllPermisos()).withRel("permisos"));
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).updatePermiso(id, permiso)).withRel("update"));
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PermisoController.class).deletePermiso(id)).withRel("delete"));
        
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar permiso", description = "Actualiza los datos de un permiso existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Permiso actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Permiso.class))),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de permiso inválidos")
    })
    public ResponseEntity<EntityModel<Permiso>> updatePermiso(
            @Parameter(description = "ID del permiso a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del permiso", required = true)
            @RequestBody Permiso permiso) {
        Permiso existingPermiso = permisoService.findById(id);
        if (existingPermiso == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            existingPermiso.setNombre(permiso.getNombre());
            existingPermiso.setDescripcion(permiso.getDescripcion());

            Permiso permisoActualizado = permisoService.save(existingPermiso);
            
            EntityModel<Permiso> entityModel = EntityModel.of(permisoActualizado);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PermisoController.class).getPermisoById(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PermisoController.class).getAllPermisos()).withRel("permisos"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PermisoController.class).updatePermiso(id, permiso)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PermisoController.class).deletePermiso(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar permiso", description = "Elimina un permiso del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Permiso eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Permiso no encontrado")
    })
    public ResponseEntity<Void> deletePermiso(
            @Parameter(description = "ID del permiso a eliminar", required = true)
            @PathVariable("id") Long id) {
        try {
            permisoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
