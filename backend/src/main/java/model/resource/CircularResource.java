package model.resource;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import model.Circular;
import model.dao.FactoryDAO;
import model.dao.FactoryEM;
import model.interfaceDao.ICircularDAO;

@Path("/circular")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CircularResource {
	private final ICircularDAO circularDao=new FactoryDAO(FactoryEM.getEntityManager()).getCircularDAO();
	
	@GET
	public List<Circular> obtenerTodos(){
		return circularDao.getCirculares();
	}
	
	@GET
	@Path("/{id}")
	public Response obtenerPorId(@PathParam("id") Long id) {
		Circular circular=circularDao.getByID(id);
		if (circular == null) 
			return Response.status(Status.NOT_FOUND).entity("No se encontró la Circular").build();
		return Response.ok(circular).build();
		
	}
	
	@PUT
	public Response modificar(Circular circ) {
		Circular c=circularDao.getByID(circ.getId());
		if(c==null) 
			return Response.status(Status.NOT_FOUND).entity("[]").build();
		
		circularDao.modifyCircular(circ);
		return Response.ok(circ).build();
	}
	
	@POST
	public Response crearCircular(Circular circular) {
		try {
			circularDao.guardar(circular);
			return Response.status(Status.CREATED).entity(circular).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("No se pudo crear la Circular").build();
		}
	}
	

	
	@DELETE
	@Path("/{id}")
	public Response eliminar(@PathParam("id") Long id) {
		Circular circular= circularDao.getByID(id);
		if(circular!=null){
			circularDao.deleteByID(id);
			return Response.noContent().build();
		}
		return Response.status(Status.NOT_FOUND).entity("No existe la circular").build();
	}
	
	
	
	
}