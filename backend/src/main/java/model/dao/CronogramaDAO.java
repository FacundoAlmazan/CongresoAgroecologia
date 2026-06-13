package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Cronograma;
import model.interfaceDao.ICronogramaDAO;

@RequestScoped
public class CronogramaDAO extends GenericDAO<Cronograma> implements ICronogramaDAO {

    @Inject
    public CronogramaDAO(EntityManager em) {
        super(em, Cronograma.class);
    }

    @Override
    public List<Cronograma> getCronogramas() {
        return listarTodos();
    }

    @Override
    public void saveCronograma(Cronograma entidad) {
        guardar(entidad);
    }

    @Override
    public Cronograma modifyCronograma(Cronograma entidad) {
        return modificar(entidad);
    }

    @Override
    public Cronograma getByIDConActividades(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT c FROM Cronograma c LEFT JOIN FETCH c.actividades WHERE c.id = :id",
                    Cronograma.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
