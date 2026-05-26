package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Evaluacion;
import model.interfaceDao.IEvaluacionDAO;

public class EvaluacionDAO extends GenericDAO<Evaluacion> implements IEvaluacionDAO {

    public EvaluacionDAO(EntityManager em) {
        super(em, Evaluacion.class);
    }

    @Override
    public List<Evaluacion> getEvaluaciones() {
        return listarTodos();
    }

    @Override
    public void saveEvaluacion(Evaluacion entidad) {
        guardar(entidad);
    }

    @Override
    public Evaluacion modifyEvaluacion(Evaluacion entidad) {
        return modificar(entidad);
    }
}
