package model.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "API Grupo 7 - Sistema de gestion del V Congreso Argentino de Agroecologia",
        version = "1.0.0",
        description = "Documentacion OpenAPI de la API REST desarrollada con Jersey, JPA y CDI para la cuarta entrega de JyAA.",
        contact = @Contact(name = "Grupo 7")
    ),
    servers = {
        @Server(description = "Servidor local", url = "http://localhost:8080/practica-jpa/api"),
        @Server(description = "Servidor de la catedra", url = "https://grupo7.jyaa-ci.linti.unlp.edu.ar/api")
    }
)
public class OpenApiConfig {
}
