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
import model.Actividad;
import model.service.ActividadService;

@Tag(name = "Actividad", description = "Gestion de actividades del congreso")
@Path("/actividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActividadResource {

    @Inject
    private ActividadService actividadService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(actividadService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(actividadService.obtener(id)).build(); }

    @POST
    @Operation(summary = "Crear recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Actividad a crear. El cronograma.id debe existir en la base",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"titulo\":\"Charla de prueba\",\"descripcion\":\"Actividad creada desde Swagger\",\"aula\":\"Aula 1\",\"fecha\":\"2026-06-17\",\"horaInicio\":\"09:00:00\",\"horaFin\":\"10:00:00\",\"cronograma\":{\"id\":1}}")
        )
    )
    public Response crear(Actividad actividad) {
        Actividad creada = actividadService.crear(actividad);
        return Response.created(URI.create("/actividades/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Actividad actualizada",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"titulo\":\"Charla modificada\",\"descripcion\":\"Actividad creada desde Swagger\",\"aula\":\"Aula 1\",\"fecha\":\"2026-06-17\",\"horaInicio\":\"09:00:00\",\"horaFin\":\"10:00:00\",\"cronograma\":{\"id\":1}}")
        )
    )
    public Response modificar(@PathParam("id") Long id, Actividad actividad) { return Response.ok(actividadService.modificar(id, actividad)).build(); }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) { actividadService.eliminar(id); return Response.noContent().build(); }
}
