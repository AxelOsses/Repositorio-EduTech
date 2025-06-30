package com.edutech.usuarios_service.controller;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.service.PermisoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PermisoController.class)
public class PermisoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisoService permisoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Permiso permiso;

    @BeforeEach
    void setUp() {
        permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("READ");
        permiso.setDescripcion("Permiso de lectura");
    }

    @Test
    public void testGetAllPermisos() throws Exception {
        when(permisoService.findAll()).thenReturn(List.of(permiso));

        mockMvc.perform(get("/api/v1/permisos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.permisoList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.permisoList[0].nombre").value("READ"))
                .andExpect(jsonPath("$._embedded.permisoList[0].descripcion").value("Permiso de lectura"));
    }

    @Test
    public void testGetPermisoById() throws Exception {
        when(permisoService.findById(1L)).thenReturn(permiso);

        mockMvc.perform(get("/api/v1/permisos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("READ"))
                .andExpect(jsonPath("$.descripcion").value("Permiso de lectura"));
    }

    @Test
    public void testCreatePermiso() throws Exception {
        Permiso permisoRequest = new Permiso();
        permisoRequest.setNombre("READ");
        permisoRequest.setDescripcion("Permiso de lectura");

        when(permisoService.save(any(Permiso.class))).thenReturn(permiso);

        mockMvc.perform(post("/api/v1/permisos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permisoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("READ"))
                .andExpect(jsonPath("$.descripcion").value("Permiso de lectura"));
    }

    @Test
    public void testUpdatePermiso() throws Exception {
        when(permisoService.findById(1L)).thenReturn(permiso);
        when(permisoService.save(any(Permiso.class))).thenReturn(permiso);

        mockMvc.perform(put("/api/v1/permisos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(permiso)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("READ"))
                .andExpect(jsonPath("$.descripcion").value("Permiso de lectura"));
    }

    @Test
    public void testDeletePermiso() throws Exception {
        doNothing().when(permisoService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/permisos/1"))
                .andExpect(status().isNoContent());

        verify(permisoService, times(1)).deleteById(1L);
    }
}
