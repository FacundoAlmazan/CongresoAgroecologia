package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.AsignacionEvaluacion;
import model.interfaceDao.IAsignacionEvaluacionDAO;
import model.enums.EstadoAsignacion;

@RequestScoped
public class AsignacionEvaluacionDAO extends GenericDAO<AsignacionEvaluacion> implements IAsignacionEvaluacionDAO {

    @Inject
    public AsignacionEvaluacionDAO(EntityManager em) {
        super(em, AsignacionEvaluacion.class);
    }

    @Override
    public List<AsignacionEvaluacion> getAsignacionesEvaluacion() {
        return listarTodos();
    }

    @Override
    public void saveAsignacionEvaluacion(AsignacionEvaluacion entidad) {
        guardar(entidad);
    }

    @Override
    public AsignacionEvaluacion modifyAsignacionEvaluacion(AsignacionEvaluacion entidad) {
        return modificar(entidad);
    }

    @Override
    public AsignacionEvaluacion getPorTrabajoYEvaluador(Long trabajoId, Long evaluadorId) {
        try {
            return em.createQuery(
                    """
                    SELECT a
                    FROM AsignacionEvaluacion a
                    WHERE a.trabajo.id = :trabajoId
                      AND a.evaluador.id = :evaluadorId
                    """,
                    AsignacionEvaluacion.class)
                .setParameter("trabajoId", trabajoId)
                .setParameter("evaluadorId", evaluadorId)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<AsignacionEvaluacion> getByEstado(EstadoAsignacion estado) {
        return em.createQuery("SELECT a FROM AsignacionEvaluacion a WHERE a.estado = :estado", AsignacionEvaluacion.class)
                .setParameter("estado", estado)
                .getResultList();
    }
}
