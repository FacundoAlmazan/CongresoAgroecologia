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
import model.AsignacionEvaluacion;
import model.service.AsignacionEvaluacionService;

@Path("/asignaciones-evaluacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AsignacionEvaluacionResource {

    @Inject
    private AsignacionEvaluacionService asignacionService;

    @GET
    public Response listar() { return Response.ok(asignacionService.listar()).build(); }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(asignacionService.obtener(id)).build(); }

    @POST
    public Response crear(AsignacionEvaluacion asignacion) {
        AsignacionEvaluacion creada = asignacionService.crear(asignacion);
        return Response.created(URI.create("/asignaciones-evaluacion/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, AsignacionEvaluacion asignacion) { return Response.ok(asignacionService.modificar(id, asignacion)).build(); }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) { asignacionService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/aceptar")
    public Response aceptar(@PathParam("id") Long id) { return Response.ok(asignacionService.aceptar(id)).build(); }

    @PUT
    @Path("/{id}/rechazar")
    public Response rechazar(@PathParam("id") Long id) { return Response.ok(asignacionService.rechazar(id)).build(); }
}
