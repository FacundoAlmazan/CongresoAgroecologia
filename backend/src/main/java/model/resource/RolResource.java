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

}
