package com.edutech.usuarios_service.util;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.model.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeDataGeneratorTest {

    @Autowired
    private FakeDataGenerator fakeDataGenerator;

    @Test
    void testGenerateRandomUsuario() {
        Usuario usuario = fakeDataGenerator.generateRandomUsuario();
        
        assertNotNull(usuario);
        assertNotNull(usuario.getNombre());
        assertNotNull(usuario.getApellido());
        assertNotNull(usuario.getEmail());
        assertNotNull(usuario.getUsername());
        assertNotNull(usuario.getPassword());
        assertNotNull(usuario.getFechaCreacion());
        assertNotNull(usuario.getEstaActivo());
        
        assertFalse(usuario.getNombre().isEmpty());
        assertFalse(usuario.getApellido().isEmpty());
        assertTrue(usuario.getEmail().contains("@"));
        assertFalse(usuario.getUsername().isEmpty());
    }

    @Test
    void testGenerateUsuarioWithSpecificParameters() {
        String nombre = "Juan";
        String apellido = "Pérez";
        String email = "juan.perez@edutech.com";
        String username = "jperez";
        String password = "password123";
        
        Usuario usuario = fakeDataGenerator.generateUsuario(nombre, apellido, email, username, password);
        
        assertEquals(nombre, usuario.getNombre());
        assertEquals(apellido, usuario.getApellido());
        assertEquals(email, usuario.getEmail());
        assertEquals(username, usuario.getUsername());
        assertEquals(password, usuario.getPassword());
    }

    @Test
    void testGenerateRandomUsuarios() {
        int cantidad = 10;
        List<Usuario> usuarios = fakeDataGenerator.generateRandomUsuarios(cantidad);
        
        assertEquals(cantidad, usuarios.size());
        
        for (Usuario usuario : usuarios) {
            assertNotNull(usuario);
            assertNotNull(usuario.getNombre());
            assertNotNull(usuario.getApellido());
            assertNotNull(usuario.getEmail());
            assertNotNull(usuario.getUsername());
        }
    }

    @Test
    void testGenerateRandomRol() {
        Rol rol = fakeDataGenerator.generateRandomRol();
        
        assertNotNull(rol);
        assertNotNull(rol.getNombre());
        assertNotNull(rol.getDescripcion());
        assertNotNull(rol.getFechaCreacion());
        
        assertFalse(rol.getNombre().isEmpty());
        assertFalse(rol.getDescripcion().isEmpty());
    }

    @Test
    void testGenerateRolWithSpecificParameters() {
        String nombre = "ADMINISTRADOR";
        String descripcion = "Rol con acceso completo al sistema";
        
        Rol rol = fakeDataGenerator.generateRol(nombre, descripcion);
        
        assertEquals(nombre, rol.getNombre());
        assertEquals(descripcion, rol.getDescripcion());
    }

    @Test
    void testGenerateRandomRoles() {
        int cantidad = 5;
        List<Rol> roles = fakeDataGenerator.generateRandomRoles(cantidad);
        
        assertEquals(cantidad, roles.size());
        
        for (Rol rol : roles) {
            assertNotNull(rol);
            assertNotNull(rol.getNombre());
            assertNotNull(rol.getDescripcion());
        }
    }

    @Test
    void testGenerateRandomPermiso() {
        Permiso permiso = fakeDataGenerator.generateRandomPermiso();
        
        assertNotNull(permiso);
        assertNotNull(permiso.getNombre());
        assertNotNull(permiso.getDescripcion());
        
        assertFalse(permiso.getNombre().isEmpty());
        assertFalse(permiso.getDescripcion().isEmpty());
    }

    @Test
    void testGeneratePermisoWithSpecificParameters() {
        String nombre = "USUARIO_CREAR";
        String descripcion = "Permite crear nuevos usuarios";
        
        Permiso permiso = fakeDataGenerator.generatePermiso(nombre, descripcion);
        
        assertEquals(nombre, permiso.getNombre());
        assertEquals(descripcion, permiso.getDescripcion());
    }

    @Test
    void testGenerateRandomPermisos() {
        int cantidad = 8;
        List<Permiso> permisos = fakeDataGenerator.generateRandomPermisos(cantidad);
        
        assertEquals(cantidad, permisos.size());
        
        for (Permiso permiso : permisos) {
            assertNotNull(permiso);
            assertNotNull(permiso.getNombre());
            assertNotNull(permiso.getDescripcion());
        }
    }

    @Test
    void testGenerateRandomPermisosSet() {
        int cantidad = 5;
        Set<Permiso> permisos = fakeDataGenerator.generateRandomPermisosSet(cantidad);
        
        assertEquals(cantidad, permisos.size());
        
        for (Permiso permiso : permisos) {
            assertNotNull(permiso);
            assertNotNull(permiso.getNombre());
            assertNotNull(permiso.getDescripcion());
        }
    }

    @Test
    void testGenerateRandomNombre() {
        String nombre = fakeDataGenerator.generateRandomNombre();
        
        assertNotNull(nombre);
        assertFalse(nombre.isEmpty());
    }

    @Test
    void testGenerateRandomApellido() {
        String apellido = fakeDataGenerator.generateRandomApellido();
        
        assertNotNull(apellido);
        assertFalse(apellido.isEmpty());
    }

    @Test
    void testGenerateRandomEmail() {
        String email = fakeDataGenerator.generateRandomEmail();
        
        assertNotNull(email);
        assertTrue(email.contains("@"));
        assertTrue(email.contains("."));
    }

    @Test
    void testGenerateRandomUsername() {
        String username = fakeDataGenerator.generateRandomUsername();
        
        assertNotNull(username);
        assertFalse(username.isEmpty());
    }

    @Test
    void testGenerateRandomFechaPasado() {
        int diasMaximos = 30;
        LocalDateTime fecha = fakeDataGenerator.generateRandomFechaPasado(diasMaximos);
        
        assertNotNull(fecha);
        assertTrue(fecha.isBefore(LocalDateTime.now()) || fecha.isEqual(LocalDateTime.now()));
        assertTrue(fecha.isAfter(LocalDateTime.now().minusDays(diasMaximos)) || fecha.isEqual(LocalDateTime.now().minusDays(diasMaximos)));
    }

    @Test
    void testGenerateTestUsuario() {
        Usuario usuario = fakeDataGenerator.generateTestUsuario();
        
        assertEquals("Test", usuario.getNombre());
        assertEquals("Usuario", usuario.getApellido());
        assertEquals("test@edutech.com", usuario.getEmail());
        assertEquals("testuser", usuario.getUsername());
        assertEquals("test123", usuario.getPassword());
        assertTrue(usuario.getEstaActivo());
    }

    @Test
    void testGenerateTestRol() {
        Rol rol = fakeDataGenerator.generateTestRol();
        
        assertEquals("TEST_ROL", rol.getNombre());
        assertEquals("Rol para testing", rol.getDescripcion());
        assertTrue(rol.isEstaActivo());
    }

    @Test
    void testGenerateTestPermiso() {
        Permiso permiso = fakeDataGenerator.generateTestPermiso();
        
        assertEquals("TEST_PERMISO", permiso.getNombre());
        assertEquals("Permiso para testing", permiso.getDescripcion());
    }
} 