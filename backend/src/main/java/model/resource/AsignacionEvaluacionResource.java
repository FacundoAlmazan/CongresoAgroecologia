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
import model.AsignacionEvaluacion;
import model.service.AsignacionEvaluacionService;

@Tag(name = "AsignacionEvaluacion", description = "Gestion de asignaciones de trabajos a evaluadores")
@Path("/asignaciones-evaluacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AsignacionEvaluacionResource {

    @Inject
    private AsignacionEvaluacionService asignacionService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(asignacionService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(asignacionService.obtener(id)).build(); }

    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(AsignacionEvaluacion asignacion) {
        AsignacionEvaluacion creada = asignacionService.crear(asignacion);
        return Response.created(URI.create("/asignaciones-evaluacion/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, AsignacionEvaluacion asignacion) { return Response.ok(asignacionService.modificar(id, asignacion)).build(); }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) { asignacionService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/aceptar")
    @Operation(summary = "Aceptar asignacion")
    public Response aceptar(@PathParam("id") Long id) { return Response.ok(asignacionService.aceptar(id)).build(); }

    @PUT
    @Path("/{id}/rechazar")
    @Operation(summary = "Rechazar asignacion o pago")
    public Response rechazar(@PathParam("id") Long id) { return Response.ok(asignacionService.rechazar(id)).build(); }
}
