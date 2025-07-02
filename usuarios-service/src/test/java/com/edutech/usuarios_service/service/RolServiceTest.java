package com.edutech.usuarios_service.service;

import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.repository.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolService rolService;

    private Rol rol;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rol = new Rol();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador del sistema");
    }

    @Test
    public void testFindAll() {
        when(rolRepository.findAll()).thenReturn(List.of(rol));
        List<Rol> roles = rolService.findAll();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals("ADMIN", roles.get(0).getNombre());
        verify(rolRepository, times(1)).findAll();
    }

    @Test
    public void testBuscarPorId_Rol() {
        when(rolRepository.findById(1L)).thenReturn(java.util.Optional.of(rol));
        Rol encontrado = rolService.findById(1L);
        assertNotNull(encontrado);
        assertEquals("ADMIN", encontrado.getNombre());
        verify(rolRepository, times(1)).findById(1L);
    }

    @Test
    public void testGuardar_Rol() {
        when(rolRepository.save(rol)).thenReturn(rol);
        Rol guardado = rolService.save(rol);
        assertNotNull(guardado);
        assertEquals("ADMIN", guardado.getNombre());
        verify(rolRepository, times(1)).save(rol);
    }

    @Test
    public void testEliminarPorId_Rol() {
        doNothing().when(rolRepository).deleteById(1L);
        rolService.deleteById(1L);
        verify(rolRepository, times(1)).deleteById(1L);
    }
}
