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

import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.service.UsuarioService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
//import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Obtiene una lista de todos los usuarios registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listar(){
        List<Usuario> usuarios = usuarioService.findAll();
        if(usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<Usuario>> usuariosConLinks = usuarios.stream()
            .map(usuario -> {
                EntityModel<Usuario> entityModel = EntityModel.of(usuario);
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(usuario.getId())).withSelfRel());
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios"));
                return entityModel;
            })
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(usuariosConLinks);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    public ResponseEntity<EntityModel<Usuario>> guardar(
            @Parameter(description = "Datos del usuario a crear", required = true)
            @RequestBody @Valid Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.save(usuario);
        EntityModel<Usuario> entityModel = EntityModel.of(nuevoUsuario);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(nuevoUsuario.getId())).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios"));
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> buscar(
            @Parameter(description = "ID del usuario a buscar", required = true)
            @PathVariable("id") Long id) {
        try {
            Usuario usuario = usuarioService.findById(id);
            if (usuario == null) {
                return ResponseEntity.notFound().build();
            }
            EntityModel<Usuario> entityModel = EntityModel.of(usuario);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).actualizar(id, new Usuario())).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).eliminar(id)).withRel("delete"));
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    public ResponseEntity<EntityModel<Usuario>> actualizar(
            @Parameter(description = "ID del usuario a actualizar", required = true)
            @PathVariable("id") Long id, 
            @Parameter(description = "Nuevos datos del usuario", required = true)
            @RequestBody @Valid Usuario usuario) {
        Usuario existingUsuario = usuarioService.findById(id);
        if (existingUsuario == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setApellido(usuario.getApellido());
            existingUsuario.setEmail(usuario.getEmail());
            existingUsuario.setUsername(usuario.getUsername());
            existingUsuario.setPassword(usuario.getPassword());
            Usuario usuarioActualizado = usuarioService.save(existingUsuario);
            EntityModel<Usuario> entityModel = EntityModel.of(usuarioActualizado);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).listar()).withRel("usuarios"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).actualizar(id, usuario)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UsuarioController.class).eliminar(id)).withRel("delete"));
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

     @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del usuario a eliminar", required = true)
            @PathVariable("id") Long id) {
        try {
            usuarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
     }
}
