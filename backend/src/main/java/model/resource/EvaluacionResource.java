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
import java.util.List;

import model.Evaluacion;
import model.interfaceDao.IEvaluacionDAO;
import model.service.EvaluacionService;

@Tag(name = "Evaluacion", description = "Endpoints REST para Evaluacion")
@Path("/evaluaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvaluacionResource {

    @Inject
    private IEvaluacionDAO dao;
    
    @Inject
    private EvaluacionService evaluacionService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() {
        return Response.ok(dao.listarTodos()).build();
    }

    
    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener recurso por id")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Evaluacion entidad = dao.getByID(id);
        if (entidad == null) {
            throw new NotFoundException("Evaluacion no encontrado con id " + id);
        }
        return Response.ok(entidad).build();
    }
    @GET
    @Path("/trabajo/{id}")
    public Response getEvaluacionesTrabajo(@PathParam("id") Long trabajoId) {
        try {

            List<Evaluacion> evaluaciones =
                    evaluacionService.getEvaluacionesPorTrabajo(trabajoId);

            return Response.ok(evaluaciones).build();

        } catch (IllegalArgumentException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocurrió un error al obtener las evaluaciones del trabajo.")
                    .build();
        }
    }
    
    @GET
    @Path("/evaluador/{id}")
    public Response getEvaluacionesEvaluador(@PathParam("id") Long evaluadorId) {
        try {

            List<Evaluacion> evaluaciones =
                    evaluacionService.getEvaluacionesPorEvaluador(evaluadorId);

            return Response.ok(evaluaciones).build();

        } catch (IllegalArgumentException e) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();

        } catch (Exception e) {

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Ocurrió un error al obtener las evaluaciones del rvaluador.")
                    .build();
        }
    }
    
    
    
    @POST
    @Operation(summary = "Crear recurso")
    public Response crear(Evaluacion entidad) {
        dao.guardar(entidad);
        return Response.created(URI.create("/evaluaciones/" + entidad.getId()))
                .entity(entidad)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Modificar recurso")
    public Response modificar(@PathParam("id") Long id, Evaluacion entidad) {
        if (dao.getByID(id) == null) {
            throw new NotFoundException("Evaluacion no encontrado con id " + id);
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
            throw new NotFoundException("Evaluacion no encontrado con id " + id);
        }
        return Response.noContent().build();
    }
}
