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
import java.util.Map;
import model.AsignacionEvaluacion;
import model.Trabajo;
import model.service.TrabajoService;

@Tag(name = "Trabajo", description = "Gestion de trabajos cientificos y relatos de experiencia")
@Path("/trabajos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrabajoResource {

    @Inject
    private TrabajoService trabajoService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(trabajoService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(trabajoService.obtener(id)).build(); }

    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(Trabajo trabajo) {
        Trabajo creado = trabajoService.crear(trabajo);
        return Response.created(URI.create("/trabajos/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, Trabajo trabajo) { return Response.ok(trabajoService.modificar(id, trabajo)).build(); }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) { trabajoService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/enviar")
    @Operation(summary = "Enviar trabajo")
    public Response enviar(@PathParam("id") Long id) { return Response.ok(trabajoService.enviar(id)).build(); }

    @POST
    @Path("/{id}/asignar-evaluador")
    @Operation(summary = "Asignar evaluador a trabajo")
    public Response asignarEvaluador(@PathParam("id") Long trabajoId, Map<String, Long> body) {
        AsignacionEvaluacion asignacion = trabajoService.asignarEvaluador(trabajoId, body.get("evaluadorId"), body.get("asignadoPorId"));
        return Response.status(Response.Status.CREATED).entity(asignacion).build();
    }

    @DELETE
    @Path("/asignaciones/{asignacionId}")
    @Operation(summary = "Desasignar evaluador de trabajo")
    public Response desasignarEvaluador(@PathParam("asignacionId") Long asignacionId) {
        trabajoService.desasignarEvaluador(asignacionId);
        return Response.noContent().build();
    }
}
