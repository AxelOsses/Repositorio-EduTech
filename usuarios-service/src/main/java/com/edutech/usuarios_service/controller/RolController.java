package com.edutech.usuarios_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "API para gestión de roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    @Operation(summary = "Listar todos los roles", description = "Obtiene una lista de todos los roles registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "204", description = "No hay roles registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Rol>>> getAllRols() {
        List<Rol> rols = rolService.findAll();
        if (rols.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<Rol>> rolesConLinks = rols.stream()
            .map(rol -> {
                EntityModel<Rol> entityModel = EntityModel.of(rol);
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RolController.class).getRolById(rol.getId())).withSelfRel());
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RolController.class).getAllRols()).withRel("roles"));
                return entityModel;
            })
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Rol>> collectionModel = CollectionModel.of(rolesConLinks);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).getAllRols()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @PostMapping
    @Operation(summary = "Crear un nuevo rol", description = "Registra un nuevo rol en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Rol creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "400", description = "Datos de rol inválidos")
    })
    public ResponseEntity<EntityModel<Rol>> createRol(
            @Parameter(description = "Datos del rol a crear", required = true)
            @RequestBody Rol rol) {
        if (rol.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        Rol savedRol = rolService.save(rol);
        
        EntityModel<Rol> entityModel = EntityModel.of(savedRol);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).getRolById(savedRol.getId())).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).getAllRols()).withRel("roles"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar rol por ID", description = "Obtiene un rol específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<EntityModel<Rol>> getRolById(
            @Parameter(description = "ID del rol a buscar", required = true)
            @PathVariable("id") Long id) {
        Rol rol = rolService.findById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        
        EntityModel<Rol> entityModel = EntityModel.of(rol);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).getRolById(id)).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).getAllRols()).withRel("roles"));
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).updateRol(id, rol)).withRel("update"));
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(RolController.class).deleteRol(id)).withRel("delete"));
        
        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Rol.class))),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de rol inválidos")
    })
    public ResponseEntity<EntityModel<Rol>> updateRol(
            @Parameter(description = "ID del rol a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del rol", required = true)
            @RequestBody Rol rol) {
        Rol existingRol = rolService.findById(id);
        if (existingRol == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            existingRol.setNombre(rol.getNombre());
            existingRol.setDescripcion(rol.getDescripcion());
            existingRol.setPermisos(rol.getPermisos());
            existingRol.setUsuarios(rol.getUsuarios());
            existingRol.setFechaCreacion(rol.getFechaCreacion());
            existingRol.setEstaActivo(rol.isEstaActivo());

            Rol rolActualizado = rolService.save(existingRol);
            
            EntityModel<Rol> entityModel = EntityModel.of(rolActualizado);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RolController.class).getRolById(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RolController.class).getAllRols()).withRel("roles"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RolController.class).updateRol(id, rol)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(RolController.class).deleteRol(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    public ResponseEntity<Void> deleteRol(
            @Parameter(description = "ID del rol a eliminar", required = true)
            @PathVariable("id") Long id) {
        try {
            rolService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
