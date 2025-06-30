package com.edutech.usuarios_service.service;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.repository.PermisoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PermisoServiceTest {

    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoService permisoService;

    private Permiso permiso;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("READ");
        permiso.setDescripcion("Permiso de lectura");
    }

    @Test
    public void testFindAll() {
        when(permisoRepository.findAll()).thenReturn(List.of(permiso));
        List<Permiso> permisos = permisoService.findAll();
        assertNotNull(permisos);
        assertEquals(1, permisos.size());
        assertEquals("READ", permisos.get(0).getNombre());
        verify(permisoRepository, times(1)).findAll();
    }
}
