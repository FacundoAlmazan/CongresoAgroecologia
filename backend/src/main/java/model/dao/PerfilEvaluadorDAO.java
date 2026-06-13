package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.PerfilEvaluador;
import model.interfaceDao.IPerfilEvaluadorDAO;

@RequestScoped
public class PerfilEvaluadorDAO extends GenericDAO<PerfilEvaluador> implements IPerfilEvaluadorDAO {

    @Inject
    public PerfilEvaluadorDAO(EntityManager em) {
        super(em, PerfilEvaluador.class);
    }

    @Override
    public List<PerfilEvaluador> getPerfilesEvaluador() {
        return listarTodos();
    }

    @Override
    public void savePerfilEvaluador(PerfilEvaluador entidad) {
        guardar(entidad);
    }

    @Override
    public PerfilEvaluador modifyPerfilEvaluador(PerfilEvaluador entidad) {
        return modificar(entidad);
    }

    @Override
    public PerfilEvaluador getByIDConDisponibilidades(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT p FROM PerfilEvaluador p " +
                    "LEFT JOIN FETCH p.disponibilidades d " +
                    "LEFT JOIN FETCH d.ejeTematico " +
                    "WHERE p.id = :id",
                    PerfilEvaluador.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
