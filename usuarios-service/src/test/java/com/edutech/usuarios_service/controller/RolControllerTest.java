package com.edutech.usuarios_service.controller;

import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.service.RolService;
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

@WebMvcTest(RolController.class)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;

    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador del sistema");
    }

    @Test
    public void testGetAllRoles() throws Exception {
        when(rolService.findAll()).thenReturn(List.of(rol));

        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.rolList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.rolList[0].nombre").value("ADMIN"))
                .andExpect(jsonPath("$._embedded.rolList[0].descripcion").value("Administrador del sistema"));
    }

    @Test
    public void testGetRolById() throws Exception {
        when(rolService.findById(1L)).thenReturn(rol);

        mockMvc.perform(get("/api/v1/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"))
                .andExpect(jsonPath("$.descripcion").value("Administrador del sistema"));
    }

    @Test
    public void testCreateRol() throws Exception {
        Rol rolRequest = new Rol();
        rolRequest.setNombre("ADMIN");
        rolRequest.setDescripcion("Administrador del sistema");

        when(rolService.save(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rolRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"))
                .andExpect(jsonPath("$.descripcion").value("Administrador del sistema"));
    }

    @Test
    public void testUpdateRol() throws Exception {
        when(rolService.findById(1L)).thenReturn(rol);
        when(rolService.save(any(Rol.class))).thenReturn(rol);

        mockMvc.perform(put("/api/v1/roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("ADMIN"))
                .andExpect(jsonPath("$.descripcion").value("Administrador del sistema"));
    }

    @Test
    public void testDeleteRol() throws Exception {
        doNothing().when(rolService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/roles/1"))
                .andExpect(status().isNoContent());

        verify(rolService, times(1)).deleteById(1L);
    }
}
