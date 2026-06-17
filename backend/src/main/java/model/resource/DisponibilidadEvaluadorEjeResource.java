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
import model.DisponibilidadEvaluadorEje;
import model.interfaceDao.IDisponibilidadEvaluadorEjeDAO;

@Tag(name = "DisponibilidadEvaluadorEje", description = "Endpoints REST para DisponibilidadEvaluadorEje")
@Path("/disponibilidades-evaluador-eje")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisponibilidadEvaluadorEjeResource {

    @Inject
    private IDisponibilidadEvaluadorEjeDAO dao;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() {
        return Response.ok(dao.listarTodos()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) {
        DisponibilidadEvaluadorEje entidad = dao.getByID(id);
        if (entidad == null) {
            throw new NotFoundException("DisponibilidadEvaluadorEje no encontrado con id " + id);
        }
        return Response.ok(entidad).build();
    }

    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(DisponibilidadEvaluadorEje entidad) {
        dao.guardar(entidad);
        return Response.created(URI.create("/disponibilidades-evaluador-eje/" + entidad.getId()))
                .entity(entidad)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, DisponibilidadEvaluadorEje entidad) {
        if (dao.getByID(id) == null) {
            throw new NotFoundException("DisponibilidadEvaluadorEje no encontrado con id " + id);
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
            throw new NotFoundException("DisponibilidadEvaluadorEje no encontrado con id " + id);
        }
        return Response.noContent().build();
    }
}
