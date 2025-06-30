package com.edutech.usuarios_service.controller;

import com.edutech.usuarios_service.model.UsuarioRol;
import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.service.UsuarioRolService;
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

@WebMvcTest(UsuarioRolController.class)
public class UsuarioRolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRolService usuarioRolService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioRol usuarioRol;
    private Usuario usuario;
    private Rol rol;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(10L);
        rol = new Rol();
        rol.setId(20L);

        usuarioRol = new UsuarioRol();
        usuarioRol.setId(1L);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
    }

    @Test
    public void testGetAllUsuarioRoles() throws Exception {
        when(usuarioRolService.findAll()).thenReturn(List.of(usuarioRol));

        mockMvc.perform(get("/api/v1/usuario-roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioRolList[0].id").value(1));
    }

    @Test
    public void testGetUsuarioRolById() throws Exception {
        when(usuarioRolService.findById(1L)).thenReturn(usuarioRol);

        mockMvc.perform(get("/api/v1/usuario-roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testCreateUsuarioRol() throws Exception {
        // Request sin ID pero con usuario y rol
        UsuarioRol usuarioRolRequest = new UsuarioRol();
        usuarioRolRequest.setUsuario(usuario);
        usuarioRolRequest.setRol(rol);

        when(usuarioRolService.save(any(UsuarioRol.class))).thenReturn(usuarioRol);

        mockMvc.perform(post("/api/v1/usuario-roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRolRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateUsuarioRol() throws Exception {
        when(usuarioRolService.findById(1L)).thenReturn(usuarioRol);
        when(usuarioRolService.save(any(UsuarioRol.class))).thenReturn(usuarioRol);

        mockMvc.perform(put("/api/v1/usuario-roles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRol)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testDeleteUsuarioRol() throws Exception {
        doNothing().when(usuarioRolService).delete(1L);

        mockMvc.perform(delete("/api/v1/usuario-roles/1"))
                .andExpect(status().isNoContent());

        verify(usuarioRolService, times(1)).delete(1L);
    }
}
