package model.resource;

import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import model.AutorTrabajo;
import model.dao.FactoryDAO;
import model.dao.FactoryEM;
import model.interfaceDao.IAutorTrabajoDAO;

@Path("/autorTrabajo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AutorTrabajoResource {
	private final IAutorTrabajoDAO autorDao=new FactoryDAO(FactoryEM.getEntityManager()).getAutorTrabajoDAO();
	
	@GET
	public List<AutorTrabajo> obtenerTodos(){
		return autorDao.getAutoresTrabajo();
	}
	
	@GET
	@Path("/{id}")
	public Response obtenerPorId(@PathParam("id") Long id) {
		AutorTrabajo autor=autorDao.getByID(id);
		if (autor == null) 
			return Response.status(Status.NOT_FOUND).entity("No se encontró el Autor").build();
		return Response.ok(autor).build();
		
	}
	
	//public Response modificar?   De momento lo dejo sin posibilidad
	
	@POST
	public Response crearAutor(AutorTrabajo autor) {
		//Podriamos agregar algun tipo de validacion, por ejemplo por email.
		try {
			autorDao.guardar(autor);
			return Response.status(Status.CREATED).entity(autor).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("No se pudo crear el autor").build();
		}
	}
	

	
	@DELETE
	@Path("/{id}")
	public Response eliminarAutor(@PathParam("id") Long id) {
		//Agregar funcion en service para verificar que el Trabajo no se quede sin autores
		AutorTrabajo autor= autorDao.getByID(id);
		if(autor!=null){
			autorDao.deleteByID(id);
			return Response.noContent().build();
		}
		return Response.status(Status.NOT_FOUND).entity("No existe el Autor").build();
	}
	
	
	
	
}
