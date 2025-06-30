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

import com.edutech.usuarios_service.model.UsuarioRol;
import com.edutech.usuarios_service.service.UsuarioRolService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuario-roles")
@Tag(name = "UsuarioRol", description = "API para gestión de asignaciones usuario-rol")
public class UsuarioRolController {

    @Autowired
    private UsuarioRolService usuarioRolService;

    @GetMapping()
    @Operation(summary = "Listar todas las asignaciones usuario-rol", description = "Obtiene una lista de todas las asignaciones usuario-rol registradas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de asignaciones obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioRol.class))),
        @ApiResponse(responseCode = "204", description = "No hay asignaciones registradas")
    })
    public ResponseEntity<CollectionModel<EntityModel<UsuarioRol>>> listar() {
        List<UsuarioRol> usuariosRol = usuarioRolService.findAll();
        if (usuariosRol.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<UsuarioRol>> usuariosRolConLinks = usuariosRol.stream()
            .map(usuarioRol -> {
                EntityModel<UsuarioRol> entityModel = EntityModel.of(usuarioRol);
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioRolController.class).buscar(usuarioRol.getId())).withSelfRel());
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioRolController.class).listar()).withRel("usuarios-rol"));
                return entityModel;
            })
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<UsuarioRol>> collectionModel = CollectionModel.of(usuariosRolConLinks);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioRolController.class).listar()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @PostMapping()
    @Operation(summary = "Crear una nueva asignación usuario-rol", description = "Registra una nueva asignación usuario-rol en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asignación creada exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioRol.class))),
        @ApiResponse(responseCode = "400", description = "Datos de asignación inválidos")
    })
    public ResponseEntity<EntityModel<UsuarioRol>> guardar(
            @Parameter(description = "Datos de la asignación a crear", required = true)
            @RequestBody UsuarioRol usuarioRol) {
        UsuarioRol nuevoUsuarioRol = usuarioRolService.save(usuarioRol);
        
        EntityModel<UsuarioRol> entityModel = EntityModel.of(nuevoUsuarioRol);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioRolController.class).buscar(nuevoUsuarioRol.getId())).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioRolController.class).listar()).withRel("usuarios-rol"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar asignación usuario-rol por ID", description = "Obtiene una asignación usuario-rol específica por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación encontrada exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioRol.class))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<EntityModel<UsuarioRol>> buscar(
            @Parameter(description = "ID de la asignación a buscar", required = true)
            @PathVariable("id") Long id) {
        try {
            UsuarioRol usuarioRol = usuarioRolService.findById(id);
            if (usuarioRol == null) {
                return ResponseEntity.notFound().build();
            }
            
            EntityModel<UsuarioRol> entityModel = EntityModel.of(usuarioRol);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).listar()).withRel("usuarios-rol"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).actualizar(id, usuarioRol)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).eliminar(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar asignación usuario-rol", description = "Actualiza los datos de una asignación usuario-rol existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación actualizada exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioRol.class))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos de asignación inválidos")
    })
    public ResponseEntity<EntityModel<UsuarioRol>> actualizar(
            @Parameter(description = "ID de la asignación a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos de la asignación", required = true)
            @RequestBody UsuarioRol usuarioRol) {
        UsuarioRol existingUsuarioRol = usuarioRolService.findById(id);
        if (existingUsuarioRol == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            existingUsuarioRol.setUsuario(usuarioRol.getUsuario());
            existingUsuarioRol.setRol(usuarioRol.getRol());

            UsuarioRol usuarioRolActualizado = usuarioRolService.save(existingUsuarioRol);
            
            EntityModel<UsuarioRol> entityModel = EntityModel.of(usuarioRolActualizado);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).listar()).withRel("usuarios-rol"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).actualizar(id, usuarioRol)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioRolController.class).eliminar(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar asignación usuario-rol", description = "Elimina una asignación usuario-rol del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asignación eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la asignación a eliminar", required = true)
            @PathVariable("id") Long id) {
        try {
            usuarioRolService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
