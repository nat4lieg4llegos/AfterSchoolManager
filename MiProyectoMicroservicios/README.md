# AfterSchoolManager

## Descripción del proyecto

AfterSchoolManager es una plataforma de gestión para programas de actividades
extracurriculares (talleres, tutorías, asistencia, inscripciones, inventario y
comunicación con apoderados) construida bajo una arquitectura de
microservicios con Spring Boot. El sistema permite administrar el ciclo
completo de un estudiante dentro de un programa after-school: matrícula,
inscripción a talleres, control de asistencia, seguimiento de progreso,
gestión de inventario de materiales y notificaciones a los apoderados.

## Integrantes

- Natalie Gallegos
- Jorge Araya
- Pamela Cocq

## Microservicios implementados

| # | Microservicio | Puerto | Base de datos | Responsabilidad |
|---|---|---|---|---|
| 1 | auth-service | 8092 | db_auth | Autenticación y gestión de usuarios |
| 2 | estudiante-service | 8081 | db_estudiantes | Gestión de estudiantes y contactos de emergencia |
| 3 | talleres-service | 8083 | db_talleres | Gestión de talleres y horarios |
| 4 | tutores-service | 8084 | db_tutores | Gestión de tutores y especialidades |
| 5 | actividades-service | 8085 | db_actividades | Gestión de actividades y materiales requeridos |
| 6 | asistencia-service | 8086 | db_asistencia | Registro y observaciones de asistencia |
| 7 | inscripciones-service | 8087 | db_inscripciones | Inscripciones a talleres y control de estados |
| 8 | inventario-service | 8088 | db_inventario | Gestión de artículos y movimientos de stock |
| 9 | notificaciones-service | 8089 | db_notificaciones | Envío y control de notificaciones a apoderados |
| 10 | reportes-service | 8090 | db_reportes | Reportes de progreso de estudiantes |
| 11 | apoderados-service | 8091 | db_apoderados | Gestión de apoderados y personas autorizadas |
| — | gateway-service | 8080 | — | API Gateway (punto de entrada único) |
| — | eureka-server | 8761 | — | Service Registry / Discovery |

Todos los microservicios de negocio siguen el patrón **Controller – Service –
Repository/Model**, exponen operaciones CRUD completas (`GET`, `GET/{id}`,
`POST`, `PUT`, `DELETE`), y se registran automáticamente en Eureka.

## Rutas principales del Gateway

Todas las peticiones deben pasar por el Gateway en `http://localhost:8080`:

| Ruta | Redirige a |
|---|---|
| `/api/v1/auth/**` | auth-service |
| `/api/v1/estudiantes/**` | estudiante-service |
| `/api/v1/talleres/**` | talleres-service |
| `/api/v1/tutores/**` | tutores-service |
| `/api/v1/actividades/**` | actividades-service |
| `/api/v1/asistencia/**` | asistencia-service |
| `/api/v1/inscripciones/**` | inscripciones-service |
| `/api/v1/inventario/**` | inventario-service |
| `/api/v1/notificaciones/**` | notificaciones-service |
| `/api/v1/reportes/**` | reportes-service |
| `/api/v1/apoderados/**` | apoderados-service |

## Documentación Swagger / OpenAPI

Cada microservicio expone su propia documentación Swagger UI en su puerto
correspondiente:

- Auth: `http://localhost:8092/swagger-ui.html`
- Estudiantes: `http://localhost:8081/`
- Talleres: `http://localhost:8083/swagger-ui.html`
- Tutores: `http://localhost:8084/swagger-ui.html`
- Actividades: `http://localhost:8085/swagger-ui.html`
- Asistencia: `http://localhost:8086/swagger-ui.html`
- Inscripciones: `http://localhost:8087/swagger-ui.html`
- Inventario: `http://localhost:8088/swagger-ui.html`
- Notificaciones: `http://localhost:8089/swagger-ui.html`
- Reportes: `http://localhost:8090/swagger-ui.html`
- Apoderados: `http://localhost:8091/swagger-ui.html`

## Ejecución local (Docker)

Requisitos previos:
- Docker Desktop abierto y corriendo
- Puerto 3306 libre (detener XAMPP/MySQL local si está activo)

Pasos:

```bash
cd MiProyectoMicroservicios
docker compose up -d
```

Esto levanta 14 contenedores: MariaDB, Eureka Server, API Gateway y los 11
microservicios de negocio. Verificar que todos estén activos:

```bash
docker compose ps
```

El registro de Eureka puede consultarse en `http://localhost:8761`.

Para detener y limpiar todo (incluye volúmenes de base de datos):

```bash
docker compose down -v
```

## Ejecución local (IDE, sin Docker)

Alternativamente, cada microservicio puede levantarse desde IntelliJ IDEA
ejecutando su clase `*Application.java`, siempre que:
- MariaDB/XAMPP esté corriendo en `localhost:3306`
- `eureka-server` se inicie primero
- `gateway-service` se inicie después de los demás microservicios

## Pruebas unitarias

```bash
cd <nombre-del-servicio>
mvn test
```

## Tecnologías principales

Spring Boot, Spring Cloud Gateway, Spring Cloud Netflix Eureka, Spring Data
JPA + Hibernate, Flyway, MariaDB, Spring HATEOAS, springdoc-openapi
(Swagger), JUnit 5, DataFaker, Docker & Docker Compose.
