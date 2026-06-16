# Plan de Pruebas - AfterSchool Manager

## Objetivo

Validar el correcto funcionamiento de los microservicios mediante pruebas unitarias utilizando JUnit y generación de datos ficticios con DataFaker.

---

## Herramientas

- Java 17/21
- Spring Boot
- JUnit 5
- DataFaker 2.4.2

---

## Casos de Prueba

| ID    | Caso de Prueba            | Servicio                | Objetivo                                                    | Resultado Esperado                                          | Tipo     | Estado |
|-------|---------------------------|-------------------------|-------------------------------------------------------------|-------------------------------------------------------------|----------|--------|
| CP-01 | Crear Estudiante          | estudiante-service      | Validar creación de estudiante con información válida        | El estudiante existe y posee RUT, nombre y apellido         | Unitaria | ✅ PASS |
| CP-02 | Nombre no nulo            | estudiante-service      | Validar que el nombre no sea nulo                           | El nombre existe y no está vacío                            | Unitaria | ✅ PASS |
| CP-03 | RUT no nulo               | estudiante-service      | Validar que el RUT no sea nulo                              | El RUT existe                                               | Unitaria | ✅ PASS |
| CP-04 | Fecha de nacimiento       | estudiante-service      | Validar fecha de nacimiento                                 | La fecha existe y coincide con el valor asignado            | Unitaria | ✅ PASS |
| CP-05 | Apoderado asociado        | estudiante-service      | Validar apoderado asociado al estudiante                    | El apoderadoId existe y coincide                            | Unitaria | ✅ PASS |
| CP-06 | Crear Taller              | talleres-service        | Validar creación de taller con información válida            | El taller existe con nombre, sede y cupo válidos            | Unitaria | ✅ PASS |
| CP-07 | Crear Horario             | talleres-service        | Validar horario asociado a un taller                        | El horario existe con día y horas válidas                   | Unitaria | ✅ PASS |
| CP-08 | Crear Tutor               | tutores-service         | Validar creación de tutor con información válida             | El tutor existe con RUT y nombre válidos                    | Unitaria | ✅ PASS |
| CP-09 | Crear Actividad           | actividades-service     | Validar creación de actividad con información válida         | La actividad existe con título y fecha válidos              | Unitaria | ✅ PASS |
| CP-10 | Crear Registro Asistencia | asistencia-service      | Validar creación de registro de asistencia                   | El registro existe con fechas válidas                       | Unitaria | ✅ PASS |
| CP-11 | Crear Inscripcion         | inscripciones-service   | Validar creación de inscripción con estado válido            | La inscripción existe con estado ACTIVA                     | Unitaria | ✅ PASS |
| CP-12 | Crear Articulo            | inventario-service      | Validar creación de artículo con stock válido                | El artículo existe con nombre y stock mayor a 0             | Unitaria | ✅ PASS |
| CP-13 | Crear Notificacion        | notificaciones-service  | Validar creación de notificación con tipo y mensaje válidos  | La notificación existe con tipo, mensaje y fecha válidos    | Unitaria | ✅ PASS |
| CP-14 | Crear Reporte Progreso    | reportes-service        | Validar creación de reporte de progreso                      | El reporte existe con observaciones y fecha válidos         | Unitaria | ✅ PASS |
| CP-15 | Crear Apoderado           | apoderados-service      | Validar creación de apoderado con información válida         | El apoderado existe con nombre y contacto válidos           | Unitaria | ✅ PASS |