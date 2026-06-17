package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import model.Usuario;
import model.service.UsuarioService;

@Tag(name = "Usuario", description = "Gestion de usuarios, roles y cuentas")
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    private UsuarioService usuarioService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(usuarioService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(usuarioService.obtener(id)).build(); }

    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(Usuario usuario) {
        Usuario creado = usuarioService.crear(usuario);
        return Response.created(URI.create("/usuarios/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, Usuario usuario) { return Response.ok(usuarioService.modificar(id, usuario)).build(); }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) { usuarioService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/activar")
    @Operation(summary = "Activar cuenta")
    public Response activar(@PathParam("id") Long id) { return Response.ok(usuarioService.activar(id)).build(); }

    @PUT
    @Path("/{id}/desactivar")
    @Operation(summary = "Desactivar cuenta")
    public Response desactivar(@PathParam("id") Long id) { return Response.ok(usuarioService.desactivar(id)).build(); }

    @PUT
    @Path("/{id}/roles/{tipoRol}")
    @Operation(summary = "Asignar rol a usuario")
    public Response asignarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) { return Response.ok(usuarioService.asignarRol(id, tipoRol)).build(); }

    @DELETE
    @Path("/{id}/roles/{tipoRol}")
    @Operation(summary = "Quitar rol a usuario")
    public Response quitarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) { return Response.ok(usuarioService.quitarRol(id, tipoRol)).build(); }

    @PUT
    @Path("/{id}/promover-evaluador")
    @Operation(summary = "Promover usuario a evaluador")
    public Response promoverAEvaluador(@PathParam("id") Long id) { return Response.ok(usuarioService.promoverAEvaluador(id)).build(); }
}
