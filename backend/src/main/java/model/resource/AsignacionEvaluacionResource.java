package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.net.URI;
import model.AsignacionEvaluacion;
import model.security.Secured;
import model.service.AsignacionEvaluacionService;

@Tag(name = "AsignacionEvaluacion", description = "Gestion de asignaciones de trabajos a evaluadores")
@Path("/asignaciones-evaluacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secured
public class AsignacionEvaluacionResource {

    @Inject
    private AsignacionEvaluacionService asignacionService;

    @GET
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(asignacionService.listar()).build(); }

    @GET
    @Path("/mias")
    @Operation(summary = "Listar las asignaciones del evaluador autenticado")
    public Response listarMias(@Context SecurityContext securityContext) {
        return Response.ok(asignacionService.listarPorEvaluador(
                usuarioId(securityContext))).build();
    }

    @GET
    @Path("/mias/{id}")
    @Operation(summary = "Obtener una asignación propia por id")
    public Response obtenerMia(
            @PathParam("id") Long id,
            @Context SecurityContext securityContext) {
        return Response.ok(asignacionService.obtenerParaEvaluador(
                id, usuarioId(securityContext))).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(asignacionService.obtener(id)).build(); }

    @POST
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Crear recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Asignación a crear. Los IDs deben existir",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"trabajo\":{\"id\":1},\"evaluador\":{\"id\":2},\"asignadoPor\":{\"id\":1},\"fechaAsignacion\":\"2026-06-17\"}")
        )
    )
    public Response crear(AsignacionEvaluacion asignacion) {
        AsignacionEvaluacion creada = asignacionService.crear(asignacion);
        return Response.created(URI.create("/asignaciones-evaluacion/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Modificar recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Asignación actualizada",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"trabajo\":{\"id\":1},\"evaluador\":{\"id\":2},\"asignadoPor\":{\"id\":1},\"fechaAsignacion\":\"2026-06-17\"}")
        )
    )
    public Response modificar(@PathParam("id") Long id, AsignacionEvaluacion asignacion) { return Response.ok(asignacionService.modificar(id, asignacion)).build(); }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) { asignacionService.eliminar(id); return Response.noContent().build(); }

    @PUT
    @Path("/{id}/aceptar")
    @Operation(summary = "Aceptar asignacion")
    public Response aceptar(
            @PathParam("id") Long id,
            @Context SecurityContext securityContext) {
        return Response.ok(asignacionService.aceptar(
                id, usuarioId(securityContext))).build();
    }

    @PUT
    @Path("/{id}/rechazar")
    @Operation(summary = "Rechazar asignacion o pago")
    public Response rechazar(
            @PathParam("id") Long id,
            @Context SecurityContext securityContext) {
        return Response.ok(asignacionService.rechazar(
                id, usuarioId(securityContext))).build();
    }

    private Long usuarioId(SecurityContext securityContext) {
        return Long.valueOf(securityContext.getUserPrincipal().getName());
    }
}
