package model.resource;

import model.Agenda;
import model.dao.FactoryDAO;
import model.dao.FactoryEM;
import model.Actividad;
import model.interfaceDao.IAgendaDAO;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

@Path("/agenda")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendaResource {

    // Instancia del DAO (en una app real se inyectaría con CDI o @Context)
    private final IAgendaDAO agendaDAO = new FactoryDAO(FactoryEM.getEntityManager()).getAgendaDAO();

    /** No hace falta
    // 1. Obtener todas las agendas
    @GET
    public List<Agenda> obtenerTodas() {
        return agendaDAO.findAll();
    }
**/
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        Agenda agenda = agendaDAO.getByID(id);
        if (agenda == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity("{\"error\": \"Agenda no encontrada\"}")
                           .build();
        }
        return Response.ok(agenda).build();
    }

    
    @POST
    public Response crearAgenda(Agenda agenda) {
        try {
            agendaDAO.saveAgenda(agenda);
            return Response.status(Status.CREATED).entity(agenda).build();
        } catch (Exception e) {
            return Response.status(Status.BAD_REQUEST)
                           .entity("{\"error\": \"No se pudo crear la agenda\"}")
                           .build();
        }
    }

    // 4. Agregar una actividad a una agenda específica (Maneja el conflicto de la entidad)
    @POST
    @Path("/{id}/actividades")
    public Response agregarActividad(@PathParam("id") Long id, Actividad actividad) {
        Agenda agenda = agendaDAO.getByID(id);
        if (agenda == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity("{\"error\": \"Agenda no encontrada\"}")
                           .build();
        }

        try {
            // Se ejecuta la lógica de negocio interna de la entidad Agenda
            agenda.agregarActividad(actividad);
            
            // Se actualiza el estado en la base de datos a través del DAO
            agendaDAO.modifyAgenda(agenda);
            
            return Response.ok(agenda).build();
        } catch (IllegalArgumentException e) {
            // Captura el conflicto de superposición de horarios provisto por la entidad
            return Response.status(Status.CONFLICT)
                           .entity("{\"error\": \"" + e.getMessage() + "\"}")
                           .build();
        }
    }

    // 5. Eliminar una agenda
    @DELETE
    @Path("/{id}")
    public Response eliminarAgenda(@PathParam("id") Long id) {
        Agenda agenda = agendaDAO.getByID(id);
        if (agenda == null) {
            return Response.status(Status.NOT_FOUND)
                           .entity("{\"error\": \"Agenda no encontrada\"}")
                           .build();
        }
        agendaDAO.deleteByID(id);
        return Response.noContent().build();
    }
}
