# Inventory Final - Proyecto de Inventario

Este proyecto es un sistema de inventario desarrollado con **Spring Boot 3**, **JPA**, y **JWT** para autenticación. Permite gestionar usuarios, productos, categorías, órdenes y reportes, con una interfaz REST y documentación Swagger.

---

## Tecnologías

- Java 21
- Spring Boot 3.5.5
- Spring Data JPA
- JWT (io.jsonwebtoken)
- H2 / PostgreSQL
- Swagger / OpenAPI
- Maven
- Lombok

---

## Requisitos

- Java JDK 21 o superior
- Maven 3.8+
- Base de datos (H2 para pruebas en memoria o PostgreSQL para producción)
- IDE recomendado: IntelliJ IDEA, Eclipse o VS Code

---

## Configuración

1. Clonar el repositorio:
   ```bash
   git clone <url-del-repositorio>
   cd inventoryFinal

2 Configurar base de datos en src/main/resources/application.properties

# H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

# JWT
jwt.secret=TuSecretoJWTAqui
jwt.expiration-ms=3600000


##Ejecución del proyecto

Desde Maven. 
mvn clean install -U
mvn spring-boot:run

#Desde un IDE (IntelliJ / Eclipse)
Abrir el proyecto como Maven Project
Ejecutar la clase principal InventoryFinalApplication.java (Spring Boot Application)
El servidor se levantará en http://localhost:8080