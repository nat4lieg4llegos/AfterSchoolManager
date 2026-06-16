# Plan de Pruebas - AfterSchool Manager

## Objetivo

Validar el correcto funcionamiento de los microservicios mediante pruebas unitarias utilizando JUnit y generación de datos ficticios con DataFaker.

---

## Herramientas

- Java 17/21
- Spring Boot
- JUnit 5
- Mockito
- DataFaker 2.4.2

---

## Casos de Prueba

| ID    | Caso de Prueba          | Servicio            | Objetivo                                              | Resultado Esperado                                      | Tipo     |
|-------|-------------------------|---------------------|-------------------------------------------------------|---------------------------------------------------------|----------|
| CP-01 | Crear Estudiante        | estudiante-service  | Validar creación de estudiante con información válida  | El estudiante existe y posee RUT, nombre y apellido     | Unitaria |
| CP-02 | Nombre no nulo          | estudiante-service  | Validar que el nombre no sea nulo                     | El nombre existe y no está vacío                        | Unitaria |
| CP-03 | RUT no nulo             | estudiante-service  | Validar que el RUT no sea nulo                        | El RUT existe                                           | Unitaria |
| CP-04 | Fecha de nacimiento     | estudiante-service  | Validar fecha de nacimiento                           | La fecha existe y coincide con el valor asignado        | Unitaria |
| CP-05 | Apoderado asociado      | estudiante-service  | Validar apoderado asociado al estudiante              | El apoderadoId existe y coincide con el valor asignado  | Unitaria |