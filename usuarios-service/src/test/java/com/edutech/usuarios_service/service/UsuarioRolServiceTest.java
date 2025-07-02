package com.edutech.usuarios_service.service;

import com.edutech.usuarios_service.model.UsuarioRol;
import com.edutech.usuarios_service.repository.UsuarioRolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioRolServiceTest {

    @Mock
    private UsuarioRolRepository usuarioRolRepository;

    @InjectMocks
    private UsuarioRolService usuarioRolService;

    private UsuarioRol usuarioRol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioRol = new UsuarioRol();
        usuarioRol.setId(1L);
        // Puedes agregar más atributos si es necesario
    }

    @Test
    public void testFindAll() {
        when(usuarioRolRepository.findAll()).thenReturn(List.of(usuarioRol));
        List<UsuarioRol> usuarioRoles = usuarioRolService.findAll();
        assertNotNull(usuarioRoles);
        assertEquals(1, usuarioRoles.size());
        assertEquals(1L, usuarioRoles.get(0).getId());
        verify(usuarioRolRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId_UsuarioRol() {
        when(usuarioRolRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioRol));
        UsuarioRol encontrado = usuarioRolService.findById(1L);
        assertNotNull(encontrado);
        assertEquals(1L, encontrado.getId());
        verify(usuarioRolRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardar_UsuarioRol() {
        when(usuarioRolRepository.save(usuarioRol)).thenReturn(usuarioRol);
        UsuarioRol guardado = usuarioRolService.save(usuarioRol);
        assertNotNull(guardado);
        assertEquals(1L, guardado.getId());
        verify(usuarioRolRepository, times(1)).save(usuarioRol);
    }

    @Test
    public void testEliminarPorId_UsuarioRol() {
        doNothing().when(usuarioRolRepository).deleteById(1L);
        usuarioRolService.delete(1L);
        verify(usuarioRolRepository, times(1)).deleteById(1L);
    }
}
