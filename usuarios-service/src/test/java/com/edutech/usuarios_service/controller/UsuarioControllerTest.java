package com.edutech.usuarios_service.controller;

import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuario;
    private Usuario usuarioRequest;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@edutech.com");
        usuario.setUsername("jperez");
        usuario.setPassword("password123");
        usuario.setFechaCreacion(LocalDateTime.now());
        usuario.setEstaActivo(true);

        usuarioRequest = new Usuario();
        usuarioRequest.setNombre("Juan");
        usuarioRequest.setApellido("Pérez");
        usuarioRequest.setEmail("juan.perez@edutech.com");
        usuarioRequest.setUsername("jperez");
        usuarioRequest.setPassword("password123");
    }

    @Test
    public void testGetAllUsuarios() throws Exception {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Juan"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].apellido").value("Pérez"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].email").value("juan.perez@edutech.com"))
                .andExpect(jsonPath("$._embedded.usuarioList[0].username").value("jperez"));
    }

    @Test
    public void testGetUsuarioById() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@edutech.com"))
                .andExpect(jsonPath("$.username").value("jperez"));
    }

    @Test
    public void testCreateUsuario() throws Exception {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@edutech.com"))
                .andExpect(jsonPath("$.username").value("jperez"));
    }

    @Test
    public void testUpdateUsuario() throws Exception {
        when(usuarioService.findById(1L)).thenReturn(usuario);
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.email").value("juan.perez@edutech.com"))
                .andExpect(jsonPath("$.username").value("jperez"));
    }

    @Test
    public void testDeleteUsuario() throws Exception {
        doNothing().when(usuarioService).delete(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService, times(1)).delete(1L);
    }
}
