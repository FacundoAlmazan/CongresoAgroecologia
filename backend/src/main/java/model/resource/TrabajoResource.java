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
import java.util.Map;
import model.AsignacionEvaluacion;
import model.Trabajo;
import model.service.TrabajoService;

@Path("/trabajos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrabajoResource {

    @Inject
    private TrabajoService trabajoService;

    @GET
    public Response listar() { return Response.ok(trabajoService.listar()).build(); }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(trabajoService.obtener(id)).build(); }

    @POST
    public Response crear(Trabajo trabajo) {
        Trabajo creado = trabajoService.crear(trabajo);
        return Response.created(URI.create("/trabajos/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, Trabajo trabajo) { return Response.ok(trabajoService.modificar(id, trabajo)).build(); }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) { trabajoService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/enviar")
    public Response enviar(@PathParam("id") Long id) { return Response.ok(trabajoService.enviar(id)).build(); }

    @POST
    @Path("/{id}/asignar-evaluador")
    public Response asignarEvaluador(@PathParam("id") Long trabajoId, Map<String, Long> body) {
        AsignacionEvaluacion asignacion = trabajoService.asignarEvaluador(trabajoId, body.get("evaluadorId"), body.get("asignadoPorId"));
        return Response.status(Response.Status.CREATED).entity(asignacion).build();
    }

    @DELETE
    @Path("/asignaciones/{asignacionId}")
    public Response desasignarEvaluador(@PathParam("asignacionId") Long asignacionId) {
        trabajoService.desasignarEvaluador(asignacionId);
        return Response.noContent().build();
    }
}
