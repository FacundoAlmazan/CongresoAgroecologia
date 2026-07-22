package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Evaluacion;
import model.interfaceDao.IEvaluacionDAO;

@RequestScoped
public class EvaluacionDAO extends GenericDAO<Evaluacion> implements IEvaluacionDAO {

    @Inject
    public EvaluacionDAO(EntityManager em) {
        super(em, Evaluacion.class);
    }

    @Override
    public List<Evaluacion> getEvaluaciones() {
        return listarTodos();
    }

    @Override
    public List<Evaluacion> getEvaluacionesPorTrabajo(Long trabajoId) {
        return em.createQuery(
                """
                SELECT e
                FROM Evaluacion e
                WHERE e.trabajo.id = :trabajoId
                ORDER BY e.id
                """,
                Evaluacion.class)
            .setParameter("trabajoId", trabajoId)
            .getResultList();
    }
    
    

    @Override
    public List<Evaluacion> getEvaluacionesPorEvaluador(Long evaluadorId) {
        return em.createQuery(
                """
                SELECT e
                FROM Evaluacion e
                WHERE e.evaluador.id = :evaluadorId
                """,
                Evaluacion.class)
            .setParameter("evaluadorId", evaluadorId)
            .getResultList();
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
