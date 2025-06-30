package com.edutech.usuarios_service.config;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.model.Rol;
import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.repository.PermisoRepository;
import com.edutech.usuarios_service.repository.RolRepository;
import com.edutech.usuarios_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    @Bean
    @Profile("!prod") // Solo se ejecuta en ambientes que no sean producción
    public CommandLineRunner seedData() {
        return args -> {
            log.info("Iniciando seed de datos para usuarios-service...");
            
            // Verificar si ya existen datos
            if (usuarioRepository.count() > 0) {
                log.info("Ya existen datos en la base de datos. Saltando seed...");
                return;
            }

            Faker faker = new Faker();
            
            // Crear permisos básicos
            List<Permiso> permisos = crearPermisosBasicos();
            permisoRepository.saveAll(permisos);
            log.info("Se han creado {} permisos básicos", permisos.size());

            // Crear roles básicos
            List<Rol> roles = crearRolesBasicos(permisos);
            rolRepository.saveAll(roles);
            log.info("Se han creado {} roles básicos", roles.size());

            // Crear usuarios de ejemplo
            List<Usuario> usuarios = crearUsuariosEjemplo(roles, faker);
            usuarioRepository.saveAll(usuarios);
            log.info("Se han creado {} usuarios de ejemplo", usuarios.size());
        };
    }

    private List<Permiso> crearPermisosBasicos() {
        List<Permiso> permisos = new ArrayList<>();
        
        permisos.add(new Permiso("USUARIO_CREAR", "Crear nuevos usuarios"));
        permisos.add(new Permiso("USUARIO_LEER", "Leer información de usuarios"));
        permisos.add(new Permiso("USUARIO_ACTUALIZAR", "Actualizar información de usuarios"));
        permisos.add(new Permiso("USUARIO_ELIMINAR", "Eliminar usuarios"));
        permisos.add(new Permiso("ROL_CREAR", "Crear nuevos roles"));
        permisos.add(new Permiso("ROL_LEER", "Leer información de roles"));
        permisos.add(new Permiso("ROL_ACTUALIZAR", "Actualizar roles"));
        permisos.add(new Permiso("ROL_ELIMINAR", "Eliminar roles"));
        permisos.add(new Permiso("CURSO_CREAR", "Crear nuevos cursos"));
        permisos.add(new Permiso("CURSO_LEER", "Leer información de cursos"));
        permisos.add(new Permiso("CURSO_ACTUALIZAR", "Actualizar cursos"));
        permisos.add(new Permiso("CURSO_ELIMINAR", "Eliminar cursos"));
        permisos.add(new Permiso("PAGO_CREAR", "Crear nuevos pagos"));
        permisos.add(new Permiso("PAGO_LEER", "Leer información de pagos"));
        permisos.add(new Permiso("PAGO_ACTUALIZAR", "Actualizar pagos"));
        permisos.add(new Permiso("PAGO_ELIMINAR", "Eliminar pagos"));
        
        return permisos;
    }

    private List<Rol> crearRolesBasicos(List<Permiso> permisos) {
        List<Rol> roles = new ArrayList<>();
        
        // Rol Administrador
        Rol admin = Rol.builder()
                .nombre("ADMINISTRADOR")
                .descripcion("Rol con acceso completo al sistema")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        admin.setPermisos(new HashSet<>(permisos)); // Todos los permisos
        roles.add(admin);

        // Rol Instructor
        Set<Permiso> permisosInstructor = new HashSet<>();
        permisosInstructor.add(permisos.get(1)); // USUARIO_LEER
        permisosInstructor.add(permisos.get(8)); // CURSO_CREAR
        permisosInstructor.add(permisos.get(9)); // CURSO_LEER
        permisosInstructor.add(permisos.get(10)); // CURSO_ACTUALIZAR
        
        Rol instructor = Rol.builder()
                .nombre("INSTRUCTOR")
                .descripcion("Rol para instructores de cursos")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        instructor.setPermisos(permisosInstructor);
        roles.add(instructor);

        // Rol Estudiante
        Set<Permiso> permisosEstudiante = new HashSet<>();
        permisosEstudiante.add(permisos.get(9)); // CURSO_LEER
        permisosEstudiante.add(permisos.get(12)); // PAGO_CREAR
        permisosEstudiante.add(permisos.get(13)); // PAGO_LEER
        
        Rol estudiante = Rol.builder()
                .nombre("ESTUDIANTE")
                .descripcion("Rol para estudiantes")
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        estudiante.setPermisos(permisosEstudiante);
        roles.add(estudiante);

        return roles;
    }

    private List<Usuario> crearUsuariosEjemplo(List<Rol> roles, Faker faker) {
        List<Usuario> usuarios = new ArrayList<>();
        
        // Usuario administrador
        Usuario admin = Usuario.builder()
                .nombre("Admin")
                .apellido("Sistema")
                .email("admin@edutech.com")
                .username("admin")
                .password("admin123") // Password sin encriptar para desarrollo
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        admin.agregarRol(roles.get(0)); // ADMINISTRADOR
        usuarios.add(admin);

        // Usuario instructor
        Usuario instructor = Usuario.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .email("juan.perez@edutech.com")
                .username("jperez")
                .password("instructor123") // Password sin encriptar para desarrollo
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        instructor.agregarRol(roles.get(1)); // INSTRUCTOR
        usuarios.add(instructor);

        // Usuario estudiante
        Usuario estudiante = Usuario.builder()
                .nombre("María")
                .apellido("García")
                .email("maria.garcia@edutech.com")
                .username("mgarcia")
                .password("estudiante123") // Password sin encriptar para desarrollo
                .fechaCreacion(LocalDateTime.now())
                .estaActivo(true)
                .build();
        estudiante.agregarRol(roles.get(2)); // ESTUDIANTE
        usuarios.add(estudiante);

        // Generar usuarios aleatorios
        for (int i = 0; i < 20; i++) {
            String nombre = faker.name().firstName();
            String apellido = faker.name().lastName();
            String email = faker.internet().emailAddress();
            String username = faker.name().username();
            
            Usuario usuario = Usuario.builder()
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .username(username)
                    .password("password123") // Password sin encriptar para desarrollo
                    .fechaCreacion(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 365)))
                    .estaActivo(faker.bool().bool())
                    .build();
            
            // Asignar rol aleatorio
            Rol rolAleatorio = roles.get(faker.number().numberBetween(1, roles.size()));
            usuario.agregarRol(rolAleatorio);
            
            usuarios.add(usuario);
        }

        return usuarios;
    }
} 