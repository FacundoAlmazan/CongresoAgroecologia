package model.resource;

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

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    private UsuarioService usuarioService;

    @GET
    public Response listar() { return Response.ok(usuarioService.listar()).build(); }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(usuarioService.obtener(id)).build(); }

    @POST
    public Response crear(Usuario usuario) {
        Usuario creado = usuarioService.crear(usuario);
        return Response.created(URI.create("/usuarios/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, Usuario usuario) { return Response.ok(usuarioService.modificar(id, usuario)).build(); }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) { usuarioService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/activar")
    public Response activar(@PathParam("id") Long id) { return Response.ok(usuarioService.activar(id)).build(); }

    @PUT
    @Path("/{id}/desactivar")
    public Response desactivar(@PathParam("id") Long id) { return Response.ok(usuarioService.desactivar(id)).build(); }

    @PUT
    @Path("/{id}/roles/{tipoRol}")
    public Response asignarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) { return Response.ok(usuarioService.asignarRol(id, tipoRol)).build(); }

    @DELETE
    @Path("/{id}/roles/{tipoRol}")
    public Response quitarRol(@PathParam("id") Long id, @PathParam("tipoRol") String tipoRol) { return Response.ok(usuarioService.quitarRol(id, tipoRol)).build(); }

    @PUT
    @Path("/{id}/promover-evaluador")
    public Response promoverAEvaluador(@PathParam("id") Long id) { return Response.ok(usuarioService.promoverAEvaluador(id)).build(); }
}
