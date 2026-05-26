package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.DecisionTrabajo;
import model.interfaceDao.IDecisionTrabajoDAO;

public class DecisionTrabajoDAO extends GenericDAO<DecisionTrabajo> implements IDecisionTrabajoDAO {

    public DecisionTrabajoDAO(EntityManager em) {
        super(em, DecisionTrabajo.class);
    }

    @Override
    public List<DecisionTrabajo> getDecisionTrabajos() {
        return listarTodos();
    }

    @Override
    public void saveDecisionTrabajo(DecisionTrabajo entidad) {
        guardar(entidad);
    }

    @Override
    public DecisionTrabajo modifyDecisionTrabajo(DecisionTrabajo entidad) {
        return modificar(entidad);
    }
}
