# ProyectoEduTech

**ProyectoEduTech** es una plataforma educativa en línea compuesta por microservicios, diseñada para facilitar la gestión de usuarios, cursos, contenidos, inscripciones, pagos, soporte y más. Está orientada a instituciones que requieren soluciones escalables y desacopladas, utilizando tecnologías modernas y buenas prácticas backend.

## Arquitectura

El sistema está desarrollado bajo una arquitectura de microservicios, donde cada servicio es independiente y cumple una función específica dentro del ecosistema.

### Microservicios incluidos:

| Microservicio         | Descripción |
|------------------------|-------------|
| **usuarios-service**   | Gestión de usuarios, autenticación básica y roles. |
| **cursos-service**     | Administración de cursos, asignación de instructores y progreso de estudiantes. |
| **contenidos-service** | Creación y edición de material didáctico, con jerarquía de permisos. |
| **inscripciones-service** | Inscripción a cursos, registro de notas y seguimiento académico. |
| **ventas-service**     | Procesamiento de pagos, cupones y facturación. |
| **soporte-service**    | Sistema de tickets y soporte técnico para usuarios. |

> Microservicio opcional pendiente: `reseñas-service` (para calificaciones y comentarios sobre cursos).

##  Tecnologías

- Java 17
- Spring Boot 3.3.11
- Maven
- MySQL
- Spring Data JPA
- Lombok
- Spring DevTools
- RESTful APIs

## Estructura del Proyecto

```
ProyectoEduTech/
│
├── usuarios-service/
├── cursos-service/
├── contenidos-service/
├── inscripciones-service/
├── ventas-service/
├── soporte-service/
├── pom.xml   ← Proyecto padre (gestión de módulos)
```

## Cómo ejecutar el proyecto

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/ProyectoEduTech.git
   cd ProyectoEduTech
   ```

2. Asegúrate de tener configurada la base de datos MySQL y los archivos `application.properties` por cada servicio.

3. Desde la raíz del proyecto:
   ```bash
   mvn clean install
   ```

4. Luego puedes ejecutar cada microservicio desde su carpeta correspondiente:
   ```bash
   cd usuarios-service
   mvn spring-boot:run
   ```

## Pruebas

Cada microservicio incluye dependencias de prueba (`spring-boot-starter-test`) para pruebas unitarias y de integración.

## Notas

- El sistema aún no implementa seguridad avanzada (como JWT).
- El microservicio de notificaciones y reseñas está considerado como **opcional** para etapas futuras.
- Este proyecto es parte de una simulación académica con enfoque realista de desarrollo ágil y arquitectura moderna.

## Licencia

Este proyecto es de uso académico y libre para fines educativos.
