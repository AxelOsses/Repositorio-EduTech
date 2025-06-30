package com.edutech.ventas_service.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.service.PagoService;
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
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "API para gestión de pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Obtiene una lista de todos los pagos registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pagos obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "204", description = "No hay pagos registrados")
    })
    public ResponseEntity<CollectionModel<EntityModel<Pago>>> listar(){
        List<Pago> pagos = pagoService.findAll();
        if(pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        List<EntityModel<Pago>> pagosConLinks = pagos.stream()
            .map(pago -> {
                EntityModel<Pago> entityModel = EntityModel.of(pago);
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PagoController.class).buscar(pago.getId())).withSelfRel());
                entityModel.add(WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(PagoController.class).listar()).withRel("pagos"));
                return entityModel;
            })
            .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Pago>> collectionModel = CollectionModel.of(pagosConLinks);
        collectionModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PagoController.class).listar()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo pago", description = "Registra un nuevo pago en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pago creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "400", description = "Datos de pago inválidos")
    })
    public ResponseEntity<EntityModel<Pago>> guardar(
            @Parameter(description = "Datos del pago a crear", required = true)
            @RequestBody Pago pago) {
        Pago nuevoPago = pagoService.save(pago);
        
        EntityModel<Pago> entityModel = EntityModel.of(nuevoPago);
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PagoController.class).buscar(nuevoPago.getId())).withSelfRel());
        entityModel.add(WebMvcLinkBuilder.linkTo(
            WebMvcLinkBuilder.methodOn(PagoController.class).listar()).withRel("pagos"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }
     
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Obtiene un pago específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<EntityModel<Pago>> buscar(
            @Parameter(description = "ID del pago a buscar", required = true)
            @PathVariable("id") Integer id) {
        try {
            Pago pago = pagoService.findById(id);
            if (pago == null) {
                return ResponseEntity.notFound().build();
            }
            
            EntityModel<Pago> entityModel = EntityModel.of(pago);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).listar()).withRel("pagos"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).actualizar(id, pago)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).eliminar(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Pago.class))),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de pago inválidos")
    })
    public ResponseEntity<EntityModel<Pago>> actualizar(
            @Parameter(description = "ID del pago a actualizar", required = true)
            @PathVariable("id") Integer id, 
            @Parameter(description = "Nuevos datos del pago", required = true)
            @RequestBody Pago pago) {
        try{
            Pago pag = pagoService.findById(id);
            pag.setId(id);
            pag.setMonto(pago.getMonto());
            pag.setMetodoPago(pago.getMetodoPago());
            pag.setFechaPago(pago.getFechaPago());
            
            Pago pagoActualizado = pagoService.save(pag);
            
            EntityModel<Pago> entityModel = EntityModel.of(pagoActualizado);
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).buscar(id)).withSelfRel());
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).listar()).withRel("pagos"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).actualizar(id, pago)).withRel("update"));
            entityModel.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PagoController.class).eliminar(id)).withRel("delete"));
            
            return ResponseEntity.ok(entityModel);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }   
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    public ResponseEntity<?> eliminar(
            @Parameter(description = "ID del pago a eliminar", required = true)
            @PathVariable("id") Integer id) {
        try {
            pagoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
     }
    
    
}
