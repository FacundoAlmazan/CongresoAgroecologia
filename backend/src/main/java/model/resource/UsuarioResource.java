package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import model.Usuario;
import model.security.Secured;
import model.service.UsuarioService;

@Tag(name = "Usuario", description = "Gestión de usuarios, roles y cuentas")
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @Inject
    private UsuarioService usuarioService;

    @GET
    @Secured
    @RolesAllowed("ADMIN")
    @Operation(summary = "Listar usuarios")
    public Response listar() { return Response.ok(usuarioService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Secured
    @Operation(summary = "Obtener usuario por id")
    public Response obtenerPorId(@PathParam("id") Long id) {
        return Response.ok(usuarioService.obtener(id)).build();
    }

    // Registro público.
    @POST
    @Operation(summary = "Crear usuario")
    public Response crear(Usuario usuario) {
        Usuario creado = usuarioService.crear(usuario);
        return Response.created(URI.create("/usuarios/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    @Secured
    @Operation(summary = "Modificar usuario")
    public Response modificar(@PathParam("id") Long id, Usuario usuario) {
        return Response.ok(usuarioService.modificar(id, usuario)).build();
    }

    @DELETE
    @Path("/{id}")
    @Secured
    @RolesAllowed("ADMIN")
    public Response eliminar(@PathParam("id") Long id) {
        usuarioService.eliminar(id);
        return Response.noContent().build();
    }

    @PUT @Path("/{id}/activar") @Secured @RolesAllowed("ADMIN")
    public Response activar(@PathParam("id") Long id) { return Response.ok(usuarioService.activar(id)).build(); }

    @PUT @Path("/{id}/desactivar") @Secured @RolesAllowed("ADMIN")
    public Response desactivar(@PathParam("id") Long id) { return Response.ok(usuarioService.desactivar(id)).build(); }

    @PUT @Path("/{id}/roles/{tipoRol}") @Secured @RolesAllowed("ADMIN")
    public Response asignarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) {
        return Response.ok(usuarioService.asignarRol(id, tipoRol)).build();
    }

    @DELETE @Path("/{id}/roles/{tipoRol}") @Secured @RolesAllowed("ADMIN")
    public Response quitarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) {
        return Response.ok(usuarioService.quitarRol(id, tipoRol)).build();
    }

    @PUT @Path("/{id}/promover-evaluador") @Secured @RolesAllowed("ADMIN")
    public Response promoverAEvaluador(@PathParam("id") Long id) {
        return Response.ok(usuarioService.promoverAEvaluador(id)).build();
    }
}
