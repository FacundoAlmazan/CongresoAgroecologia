package model.interfaceDao;

import java.util.List;
import model.Trabajo;
import model.enums.EstadoTrabajo;
import model.enums.TipoTrabajo;

public interface ITrabajoDAO {

	List<Trabajo> getTrabajos();
	List<Trabajo> getByTipoTrabajo(TipoTrabajo tt);
	List<Trabajo> getByEstadoTrabajo(EstadoTrabajo et);
	
	Trabajo getByID(Long id);

	void modifyTrabajo(Trabajo a);

	boolean deleteByID(Long id);

	void saveTrabajo(Trabajo a);
	
}