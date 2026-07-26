package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.AsignacionEvaluacion;
import model.Evaluacion;
import model.Trabajo;
import model.Usuario;
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
        try {
            em.getTransaction().begin();

            AsignacionEvaluacion asignacion = em.find(
                    AsignacionEvaluacion.class,
                    entidad.getAsignacion().getId());
            asignacion.setFinalizado(true);

            entidad.setAsignacion(asignacion);
            entidad.setTrabajo(em.getReference(
                    Trabajo.class,
                    entidad.getTrabajo().getId()));
            entidad.setEvaluador(em.getReference(
                    Usuario.class,
                    entidad.getEvaluador().getId()));

            em.persist(entidad);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Evaluacion modifyEvaluacion(Evaluacion entidad) {
        return modificar(entidad);
    }
}
