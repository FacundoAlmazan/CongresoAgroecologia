package model.interfaceDao;

import java.util.List;
import model.Evaluacion;

public interface IEvaluacionDAO {

	List<Evaluacion> getEvaluaciones();

	Evaluacion getByID(Long id);

	void modifyEvaluacion(Evaluacion a);

	boolean deleteByID(Long id);

	void saveEvaluacion(Evaluacion a);
	
}