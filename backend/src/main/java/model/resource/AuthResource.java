package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.dto.LoginRequest;
import model.service.AuthService;

@Tag(name = "Autenticación")
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    @Operation(summary = "Iniciar sesión y obtener un JWT")
    public Response login(LoginRequest request) {
        return Response.ok(authService.login(request)).build();
    }
}
