package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;
import model.AsignacionEvaluacion;
import model.enums.EstadoAsignacion;
import model.interfaceDao.IAsignacionEvaluacionDAO;

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
        return em.createQuery(
                """
                SELECT a
                FROM AsignacionEvaluacion a
                WHERE a.trabajo.id = :trabajoId
                  AND a.evaluador.id = :evaluadorId
                ORDER BY a.id DESC
                """,
                AsignacionEvaluacion.class)
                .setParameter("trabajoId", trabajoId)
                .setParameter("evaluadorId", evaluadorId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<AsignacionEvaluacion> getPorEvaluador(Long evaluadorId) {
        return em.createQuery(
                """
                SELECT a
                FROM AsignacionEvaluacion a
                WHERE a.evaluador.id = :evaluadorId
                ORDER BY a.id DESC
                """,
                AsignacionEvaluacion.class)
                .setParameter("evaluadorId", evaluadorId)
                .getResultList();
    }

    @Override
    public List<AsignacionEvaluacion> getByEstado(EstadoAsignacion estado) {
        return em.createQuery("SELECT a FROM AsignacionEvaluacion a WHERE a.estado = :estado", AsignacionEvaluacion.class)
                .setParameter("estado", estado)
                .getResultList();
    }
}
