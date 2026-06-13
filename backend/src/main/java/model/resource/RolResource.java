package model.resource;

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
import model.Rol;
import model.interfaceDao.IRolDAO;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RolResource {

    @Inject
    private IRolDAO dao;

    @GET
    public Response listar() {
        return Response.ok(dao.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Rol entidad = dao.getByID(id);
        if (entidad == null) {
            throw new NotFoundException("Rol no encontrado con id " + id);
        }
        return Response.ok(entidad).build();
    }

    @POST
    public Response crear(Rol entidad) {
        dao.guardar(entidad);
        return Response.created(URI.create("/roles/" + entidad.getId()))
                .entity(entidad)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response modificar(@PathParam("id") Long id, Rol entidad) {
        if (dao.getByID(id) == null) {
            throw new NotFoundException("Rol no encontrado con id " + id);
        }
        entidad.setId(id);
        return Response.ok(dao.modificar(entidad)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = dao.deleteByID(id);
        if (!eliminado) {
            throw new NotFoundException("Rol no encontrado con id " + id);
        }
        return Response.noContent().build();
    }
}
