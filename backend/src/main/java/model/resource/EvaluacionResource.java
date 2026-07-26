package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import java.net.URI;
import java.util.List;
import model.Evaluacion;
import model.interfaceDao.IEvaluacionDAO;
import model.security.Secured;
import model.service.EvaluacionService;

@Tag(name = "Evaluacion", description = "Endpoints REST para Evaluacion")
@Path("/evaluaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secured
public class EvaluacionResource {

    @Inject
    private IEvaluacionDAO dao;
    
    @Inject
    private EvaluacionService evaluacionService;

    @GET
    @RolesAllowed({"ADMIN", "OC"})
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
    @RolesAllowed({"ADMIN", "OC"})
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
                    .entity("Ocurrió un error al obtener las evaluaciones del evaluador.")
                    .build();
        }
    }
    
    
    
    @POST
    @RolesAllowed("EVALUADOR")
    @Operation(summary = "Crear recurso")
    public Response crear(
            Evaluacion entidad,
            @Context SecurityContext securityContext) {
        Evaluacion creada =
                evaluacionService.guardarEvaluacion(entidad, usuarioId(securityContext));
        return Response.created(URI.create("/evaluaciones/" + creada.getId()))
                .entity(creada)
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "OC"})
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
    @RolesAllowed({"ADMIN", "OC"})
    @Operation(summary = "Eliminar recurso")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = dao.deleteByID(id);
        if (!eliminado) {
            throw new NotFoundException("Evaluacion no encontrado con id " + id);
        }
        return Response.noContent().build();
    }

    private Long usuarioId(SecurityContext securityContext) {
        return Long.valueOf(securityContext.getUserPrincipal().getName());
    }
}
