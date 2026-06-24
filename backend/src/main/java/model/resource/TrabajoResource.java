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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Trabajo a crear. autorContacto.id y ejesTematicos.id deben existir",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"titulo\":\"Manejo agroecológico de suelos\",\"resumen\":\"Resumen del trabajo presentado al congreso.\",\"archivo\":\"trabajo.pdf\",\"passwordTrabajo\":\"clave123\",\"telefonoContacto\":\"+5492211234567\",\"modalidadSolicitada\":\"ORAL\",\"tipo\":\"TRABAJO_CIENTIFICO\",\"autorContacto\":{\"id\":1},\"ejesTematicos\":[{\"id\":1}]}")
        )
    )
    public Response crear(Trabajo trabajo) {
        Trabajo creado = trabajoService.crear(trabajo);
        return Response.created(URI.create("/trabajos/" + creado.getId())).entity(creado).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Trabajo actualizado",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"titulo\":\"Manejo agroecológico de semillas\",\"resumen\":\"Resumen del trabajo presentado al congreso.\",\"archivo\":\"trabajo.pdf\",\"passwordTrabajo\":\"clave123\",\"telefonoContacto\":\"+5492211234567\",\"modalidadSolicitada\":\"ORAL\",\"tipo\":\"TRABAJO_CIENTIFICO\",\"autorContacto\":{\"id\":1},\"ejesTematicos\":[{\"id\":1}]}")
        )
    )
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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "IDs necesarios para asignar evaluador",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"evaluadorId\":2,\"asignadoPorId\":1}")
        )
    )
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
