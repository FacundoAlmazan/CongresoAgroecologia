package model.resource;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import model.Congreso;
import model.dao.FactoryDAO;
import model.dao.FactoryEM;
import model.interfaceDao.ICongresoDAO;

@Path("/congreso")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CongresoResource {
	private final ICongresoDAO congresoDao = new FactoryDAO(FactoryEM.getEntityManager()).getCongresoDAO();

	@GET
	public List<Congreso> obtenerTodos() {
		return congresoDao.getCongresos();
	}

	@GET
	@Path("/{id}")
	public Response obtenerPorId(@PathParam("id") Long id) {
		Congreso congreso = congresoDao.getByID(id);
		if (congreso == null)
			return Response.status(Status.NOT_FOUND).entity("No se encontró el Congreso").build();
		return Response.ok(congreso).build();
	}

	@PUT
	public Response modificar(Congreso congresoMod) {
		Congreso congresoActual = congresoDao.getByID(congresoMod.getId());
		if (congresoActual == null)
			return Response.status(Status.NOT_FOUND).entity("[]").build();

		congresoDao.modifyCongreso(congresoMod);
		return Response.ok(congresoMod).build();
	}

	@POST
	public Response crearCongreso(Congreso congreso) {
		try {
			congresoDao.guardar(congreso);
			return Response.status(Status.CREATED).entity(congreso).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("No se pudo crear el Congreso").build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		Congreso congreso = congresoDao.getByID(id);
		if (congreso != null) {
			congresoDao.deleteByID(id);
			return Response.noContent().build();
		}
		return Response.status(Status.NOT_FOUND).entity("No existe el congreso").build();
	}

	
}