package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Trabajo;
import model.interfaceDao.ITrabajoDAO;
import model.enums.EstadoTrabajo;
import model.enums.TipoTrabajo;

public class TrabajoDAO extends GenericDAO<Trabajo> implements ITrabajoDAO {

    public TrabajoDAO(EntityManager em) {
        super(em, Trabajo.class);
    }

    @Override
    public List<Trabajo> getTrabajos() {
        return listarTodos();
    }

    @Override
    public void saveTrabajo(Trabajo entidad) {
        guardar(entidad);
    }

    @Override
    public Trabajo modifyTrabajo(Trabajo entidad) {
        return modificar(entidad);
    }

    @Override
    public List<Trabajo> getByTipoTrabajo(TipoTrabajo tt) {
        return em.createQuery("SELECT t FROM Trabajo t WHERE t.tipo = :tipo", Trabajo.class)
                .setParameter("tipo", tt)
                .getResultList();
    }

    @Override
    public List<Trabajo> getByEstadoTrabajo(EstadoTrabajo et) {
        return em.createQuery("SELECT t FROM Trabajo t WHERE t.estado = :estado", Trabajo.class)
                .setParameter("estado", et)
                .getResultList();
    }

    @Override
    public Trabajo getByIDConAutoresYEjes(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT t FROM Trabajo t " +
                    "LEFT JOIN FETCH t.autores " +
                    "LEFT JOIN FETCH t.ejesTematicos " +
                    "WHERE t.id = :id",
                    Trabajo.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
