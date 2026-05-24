package model.interfaceDao;

import java.util.List;
import model.Inscripcion;
import model.enums.EstadoPago;

public interface IInscripcionDAO {

	List<Inscripcion> getInscripciones();
	List<Inscripcion> getInscripcionesByEstadoPago(EstadoPago p);
	Inscripcion getByID(Long id);
	

	void modifyInscripcion(Inscripcion a);

	boolean deleteByID(Long id);

	void saveInscripcion(Inscripcion a);
	
}
