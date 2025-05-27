package com.edutech.ventas_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.ventas_service.model.Pago;
import com.edutech.ventas_service.service.PagoService;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>>listar(){
        List<Pago> pagos = pagoService.findAll();
        if(pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PostMapping
    public ResponseEntity<Pago> guardar(@RequestBody Pago pago) {
        Pago nuevoPago = pagoService.save(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }
     
    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscar(@PathVariable("id") Integer id) {
        try {
            Pago pago = pagoService.findById(id);
            if (pago == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable("id") Integer id, @RequestBody Pago pago) {
        try{
            Pago pag = pagoService.findById(id);
            pag.setId(id);
            pag.setMonto(pago.getMonto());
            pag.setMetodoPago(pago.getMetodoPago());
            pag.setFechaPago(pago.getFechaPago());
            
            pagoService.save(pag);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }   
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        try {
            pagoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
     }
    
    
}
