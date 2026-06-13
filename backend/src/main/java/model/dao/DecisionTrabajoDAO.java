package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.DecisionTrabajo;
import model.interfaceDao.IDecisionTrabajoDAO;

@RequestScoped
public class DecisionTrabajoDAO extends GenericDAO<DecisionTrabajo> implements IDecisionTrabajoDAO {

    @Inject
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
