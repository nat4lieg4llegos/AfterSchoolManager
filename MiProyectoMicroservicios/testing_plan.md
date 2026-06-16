# Plan de Pruebas - AfterSchool Manager

## Objetivo

Validar el correcto funcionamiento de los microservicios mediante pruebas unitarias utilizando JUnit y generacion de datos ficticios con DataFaker.

---

## Herramientas

- Java 17/21
- Spring Boot
- JUnit 5
- DataFaker 2.4.2

---

## Casos de Prueba

| ID    | Caso de Prueba            | Servicio                | Objetivo                                                    | Resultado Esperado                                          | Tipo     | Estado  |
|-------|---------------------------|-------------------------|-------------------------------------------------------------|-------------------------------------------------------------|----------|---------|
| CP-01 | Crear Estudiante          | estudiante-service      | Validar creacion de estudiante con informacion valida        | El estudiante existe y posee RUT, nombre y apellido         | Unitaria | ✅ PASS |
| CP-02 | Nombre no nulo            | estudiante-service      | Validar que el nombre no sea nulo                           | El nombre existe y no esta vacio                            | Unitaria | ✅ PASS |
| CP-03 | RUT no nulo               | estudiante-service      | Validar que el RUT no sea nulo                              | El RUT existe                                               | Unitaria | ✅ PASS |
| CP-04 | Fecha de nacimiento       | estudiante-service      | Validar fecha de nacimiento                                 | La fecha existe y coincide con el valor asignado            | Unitaria | ✅ PASS |
| CP-05 | Apoderado asociado        | estudiante-service      | Validar apoderado asociado al estudiante                    | El apoderadoId existe y coincide                            | Unitaria | ✅ PASS |
| CP-06 | Crear Taller              | talleres-service        | Validar creacion de taller con informacion valida            | El taller existe con nombre, sede y cupo validos            | Unitaria | ✅ PASS |
| CP-07 | Crear Horario             | talleres-service        | Validar horario asociado a un taller                        | El horario existe con dia y horas validas                   | Unitaria | ✅ PASS |
| CP-08 | Crear Tutor               | tutores-service         | Validar creacion de tutor con informacion valida             | El tutor existe con RUT y nombre validos                    | Unitaria | ✅ PASS |
| CP-09 | Crear Actividad           | actividades-service     | Validar creacion de actividad con informacion valida         | La actividad existe con titulo y fecha validos              | Unitaria | ✅ PASS |
| CP-10 | Crear Registro Asistencia | asistencia-service      | Validar creacion de registro de asistencia                   | El registro existe con fechas validas                       | Unitaria | ✅ PASS |
| CP-11 | Crear Inscripcion         | inscripciones-service   | Validar creacion de inscripcion con estado valido            | La inscripcion existe con estado ACTIVA                     | Unitaria | ✅ PASS |
| CP-12 | Crear Articulo            | inventario-service      | Validar creacion de articulo con stock valido                | El articulo existe con nombre y stock mayor a 0             | Unitaria | ✅ PASS |
| CP-13 | Crear Notificacion        | notificaciones-service  | Validar creacion de notificacion con tipo y mensaje validos  | La notificacion existe con tipo, mensaje y fecha validos    | Unitaria | ✅ PASS |
| CP-14 | Crear Reporte Progreso    | reportes-service        | Validar creacion de reporte de progreso                      | El reporte existe con observaciones y fecha validos         | Unitaria | ✅ PASS |
| CP-15 | Crear Apoderado           | apoderados-service      | Validar creacion de apoderado con informacion valida         | El apoderado existe con nombre y contacto validos           | Unitaria | ✅ PASS |

---

## Resumen de Ejecucion

| Metrica              | Valor         |
|----------------------|---------------|
| Total de pruebas     | 15            |
| Pruebas pasadas      | 15            |
| Pruebas fallidas     | 0             |
| Cobertura de servicios | 10 / 10 servicios |
| Resultado general    | ✅ TODAS PASAN |

---

## Servicios Cubiertos

| Servicio               | Clase de Test           | Tests | Estado  |
|------------------------|-------------------------|-------|---------|
| estudiante-service     | EstudianteServiceTest   | 5     | ✅ PASS |
| talleres-service       | TalleresTest            | 2     | ✅ PASS |
| tutores-service        | TutoresTest             | 1     | ✅ PASS |
| actividades-service    | ActividadesTest         | 1     | ✅ PASS |
| asistencia-service     | AsistenciaTest          | 1     | ✅ PASS |
| inscripciones-service  | InscripcionesTest       | 1     | ✅ PASS |
| inventario-service     | InventarioTest          | 1     | ✅ PASS |
| notificaciones-service | NotificacionesTest      | 1     | ✅ PASS |
| reportes-service       | ReportesTest            | 1     | ✅ PASS |
| apoderados-service     | ApoderadosTest          | 1     | ✅ PASS |

---

## Notas

- Las pruebas son unitarias puras: no se conectan a base de datos real.
- Los datos de prueba son generados con DataFaker 2.4.2 (datos aleatorios y validos).
- Patron utilizado: AAA (Arrange, Act, Assert).
- Framework: JUnit 5 con Spring Boot Test.
