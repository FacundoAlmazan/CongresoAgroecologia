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
import model.ItemAgenda;
import model.interfaceDao.IItemAgendaDAO;

@Tag(name = "ItemAgenda", description = "Endpoints REST para ItemAgenda")
@Path("/items-agenda")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemAgendaResource {

    @Inject
    private IItemAgendaDAO dao;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() {
        return Response.ok(dao.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) {
        ItemAgenda entidad = dao.getByID(id);
        if (entidad == null) {
            throw new NotFoundException("ItemAgenda no encontrado con id " + id);
        }
        return Response.ok(entidad).build();
    }

    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(ItemAgenda entidad) {
        dao.guardar(entidad);
        return Response.created(URI.create("/items-agenda/" + entidad.getId()))
                .entity(entidad)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, ItemAgenda entidad) {
        if (dao.getByID(id) == null) {
            throw new NotFoundException("ItemAgenda no encontrado con id " + id);
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
            throw new NotFoundException("ItemAgenda no encontrado con id " + id);
        }
        return Response.noContent().build();
    }
}
