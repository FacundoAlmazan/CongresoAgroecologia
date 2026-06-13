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
import model.Actividad;
import model.service.ActividadService;

@Path("/actividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActividadResource {

    @Inject
    private ActividadService actividadService;

    @GET
    public Response listar() { return Response.ok(actividadService.listar()).build(); }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) { return Response.ok(actividadService.obtener(id)).build(); }

    @POST
    public Response crear(Actividad actividad) {
        Actividad creada = actividadService.crear(actividad);
        return Response.created(URI.create("/actividades/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, Actividad actividad) { return Response.ok(actividadService.modificar(id, actividad)).build(); }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) { actividadService.eliminar(id); return Response.noContent().build(); }
}
