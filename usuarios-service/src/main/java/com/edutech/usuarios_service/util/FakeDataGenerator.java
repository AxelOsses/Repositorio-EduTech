package com.edutech.usuarios_service.util;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.model.Usuario;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class FakeDataGenerator {

    private final Faker faker = new Faker();
    private final Random random = new Random();

    // Roles disponibles
    private static final String[] ROLES_DISPONIBLES = {
        "ADMINISTRADOR", 
        "INSTRUCTOR", 
        "ESTUDIANTE", 
        "MODERADOR",
        "EDITOR",
        "REVISOR"
    };

    // Permisos disponibles
    private static final String[] PERMISOS_DISPONIBLES = {
        "USUARIO_CREAR", "USUARIO_LEER", "USUARIO_ACTUALIZAR", "USUARIO_ELIMINAR",
        "ROL_CREAR", "ROL_LEER", "ROL_ACTUALIZAR", "ROL_ELIMINAR",
        "CURSO_CREAR", "CURSO_LEER", "CURSO_ACTUALIZAR", "CURSO_ELIMINAR",
        "PAGO_CREAR", "PAGO_LEER", "PAGO_ACTUALIZAR", "PAGO_ELIMINAR",
        "EVALUACION_CREAR", "EVALUACION_LEER", "EVALUACION_ACTUALIZAR", "EVALUACION_ELIMINAR"
    };

    /**
     * Genera un usuario aleatorio
     */
    public Usuario generateRandomUsuario() {
        String nombre = faker.name().firstName();
        String apellido = faker.name().lastName();
        String email = faker.internet().emailAddress();
        String username = faker.name().username();
        
        return Usuario.builder()
                .nombre(nombre)
                .apellido(apellido)
                .email(email)
                .username(username)
                .password("password123")
                .fechaCreacion(LocalDateTime.now().minusDays(random.nextInt(365)))
                .estaActivo(faker.bool().bool())
                .build();
    }

    /**
     * Genera un usuario con parámetros específicos
     */
    public Usuario generateUsuario(String nombre, String apellido, String email, String username, String password) {
        return Usuario.builder()
                .nombre(nombre != null ? nombre : faker.name().firstName())
                .apellido(apellido != null ? apellido : faker.name().lastName())
                .email(email != null ? email : faker.internet().emailAddress())
                .username(username != null ? username : faker.name().username())
                .password(password != null ? password : "password123")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
    }

    /**
     * Genera una lista de usuarios aleatorios
     */
    public List<Usuario> generateRandomUsuarios(int cantidad) {
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            usuarios.add(generateRandomUsuario());
        }
        return usuarios;
    }

    /**
     * Genera un rol aleatorio
     */
    public Rol generateRandomRol() {
        String nombre = ROLES_DISPONIBLES[random.nextInt(ROLES_DISPONIBLES.length)];
        String descripcion = faker.lorem().sentence();
        
        return Rol.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .fechaCreacion(LocalDateTime.now().minusDays(random.nextInt(365)))
                .estaActivo(faker.bool().bool())
                .build();
    }

    /**
     * Genera un rol con parámetros específicos
     */
    public Rol generateRol(String nombre, String descripcion) {
        return Rol.builder()
                .nombre(nombre != null ? nombre : ROLES_DISPONIBLES[random.nextInt(ROLES_DISPONIBLES.length)])
                .descripcion(descripcion != null ? descripcion : faker.lorem().sentence())
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
    }

    /**
     * Genera una lista de roles aleatorios
     */
    public List<Rol> generateRandomRoles(int cantidad) {
        List<Rol> roles = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            roles.add(generateRandomRol());
        }
        return roles;
    }

    /**
     * Genera un permiso aleatorio
     */
    public Permiso generateRandomPermiso() {
        String nombre = PERMISOS_DISPONIBLES[random.nextInt(PERMISOS_DISPONIBLES.length)];
        String descripcion = faker.lorem().sentence();
        
        return new Permiso(nombre, descripcion);
    }

    /**
     * Genera un permiso con parámetros específicos
     */
    public Permiso generatePermiso(String nombre, String descripcion) {
        return new Permiso(
            nombre != null ? nombre : PERMISOS_DISPONIBLES[random.nextInt(PERMISOS_DISPONIBLES.length)],
            descripcion != null ? descripcion : faker.lorem().sentence()
        );
    }

    /**
     * Genera una lista de permisos aleatorios
     */
    public List<Permiso> generateRandomPermisos(int cantidad) {
        List<Permiso> permisos = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            permisos.add(generateRandomPermiso());
        }
        return permisos;
    }

    /**
     * Genera un conjunto aleatorio de permisos
     */
    public Set<Permiso> generateRandomPermisosSet(int cantidad) {
        Set<Permiso> permisos = new HashSet<>();
        for (int i = 0; i < cantidad; i++) {
            permisos.add(generateRandomPermiso());
        }
        return permisos;
    }

    /**
     * Genera un nombre aleatorio
     */
    public String generateRandomNombre() {
        return faker.name().firstName();
    }

    /**
     * Genera un apellido aleatorio
     */
    public String generateRandomApellido() {
        return faker.name().lastName();
    }

    /**
     * Genera un email aleatorio
     */
    public String generateRandomEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Genera un username aleatorio
     */
    public String generateRandomUsername() {
        return faker.name().username();
    }

    /**
     * Genera una fecha aleatoria en el pasado
     */
    public LocalDateTime generateRandomFechaPasado(int diasMaximos) {
        return LocalDateTime.now().minusDays(random.nextInt(diasMaximos));
    }

    /**
     * Genera datos de usuario para testing con valores específicos
     */
    public Usuario generateTestUsuario() {
        return Usuario.builder()
                .nombre("Test")
                .apellido("Usuario")
                .email("test@edutech.com")
                .username("testuser")
                .password("test123")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
    }

    /**
     * Genera datos de rol para testing con valores específicos
     */
    public Rol generateTestRol() {
        return Rol.builder()
                .nombre("TEST_ROL")
                .descripcion("Rol para testing")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
    }

    /**
     * Genera datos de permiso para testing con valores específicos
     */
    public Permiso generateTestPermiso() {
        return new Permiso("TEST_PERMISO", "Permiso para testing");
    }
} 