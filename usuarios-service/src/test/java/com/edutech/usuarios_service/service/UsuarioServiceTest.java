package com.edutech.usuarios_service.service;

import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@edutech.com");
        usuario.setUsername("jperez");
        usuario.setPassword("password123");
    }

    @Test
    public void testFindAll() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan", usuarios.get(0).getNombre());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        Usuario found = usuarioService.findById(1L);
        assertNotNull(found);
        assertEquals("Juan", found.getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    public void testSave() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario saved = usuarioService.save(usuario);
        assertNotNull(saved);
        assertEquals("Juan", saved.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void testDelete() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.delete(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
} 