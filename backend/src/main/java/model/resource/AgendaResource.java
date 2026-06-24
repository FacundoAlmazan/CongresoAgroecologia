package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import model.Agenda;
import model.interfaceDao.IAgendaDAO;

@Tag(name = "Agenda", description = "Endpoints REST para Agenda")
@Path("/agendas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendaResource {

    @Inject
    private IAgendaDAO dao;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() {
        return Response.ok(dao.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Agenda entidad = dao.getByID(id);
        if (entidad == null) {
            throw new NotFoundException("Agenda no encontrado con id " + id);
        }
        return Response.ok(entidad).build();
    }

    @POST
    @Operation(summary = "Crear agenda")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
    	    description = "Datos para crear una agenda. Usar un usuario existente.",
    	    content = @io.swagger.v3.oas.annotations.media.Content(
    	        mediaType = MediaType.APPLICATION_JSON,
    	        examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
    	            name = "Crear agenda",
    	            summary = "Reemplazar usuario.id por un ID existente",
    	            value = "{\"usuario\":{\"id\":1}}"
    	        )
    	    )
    )
    public Response crear(Agenda entidad) {
        dao.guardar(entidad);
        return Response.created(URI.create("/agendas/" + entidad.getId()))
                .entity(entidad)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos para modificar una agenda. Usar IDs existentes.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Modificar agenda",
                    summary = "Reemplazar usuario.id por un ID existente",
                    value = "{\"usuario\":{\"id\":1}}"
                )
            )
        )
    public Response modificar(@PathParam("id") Long id, Agenda entidad) {
        if (dao.getByID(id) == null) {
            throw new NotFoundException("Agenda no encontrado con id " + id);
        }
        entidad.setId(id);
        return Response.ok(dao.modificar(entidad)).build();
    }


    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = dao.deleteByID(id);
        if (!eliminado) {
            throw new NotFoundException("Agenda no encontrado con id " + id);
        }
        return Response.noContent().build();
    }
}
