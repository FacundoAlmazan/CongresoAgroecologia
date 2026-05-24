package model.interfaceDao;

import java.util.List;
import model.DecisionTrabajo;

public interface IDecisionTrabajoDAO {

	List<DecisionTrabajo> getDecisionTrabajos();

	DecisionTrabajo getByID(Long id);

	void modifyDecisionTrabajo(DecisionTrabajo a);

	boolean deleteByID(Long id);

	void saveDecisionTrabajo(DecisionTrabajo a);
	
}