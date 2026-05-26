package model.interfaceDao;

import java.util.List;
import model.DecisionTrabajo;

public interface IDecisionTrabajoDAO extends IGenericDAO<DecisionTrabajo> {
    List<DecisionTrabajo> getDecisionTrabajos();
    void saveDecisionTrabajo(DecisionTrabajo entidad);
    DecisionTrabajo modifyDecisionTrabajo(DecisionTrabajo entidad);
}
