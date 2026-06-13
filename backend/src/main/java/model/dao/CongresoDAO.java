package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Congreso;
import model.interfaceDao.ICongresoDAO;

@RequestScoped
public class CongresoDAO extends GenericDAO<Congreso> implements ICongresoDAO {

    @Inject
    public CongresoDAO(EntityManager em) {
        super(em, Congreso.class);
    }

    @Override
    public List<Congreso> getCongresos() {
        return listarTodos();
    }

    @Override
    public void saveCongreso(Congreso entidad) {
        guardar(entidad);
    }

    @Override
    public Congreso modifyCongreso(Congreso entidad) {
        return modificar(entidad);
    }

    @Override
    public Congreso getByName(String name) {
        try {
            return em.createQuery("SELECT c FROM Congreso c WHERE c.nombre = :name", Congreso.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Congreso congreso = getByName(name);
        return congreso != null && deleteByID(congreso.getId());
    }

    @Override
    public Congreso getByIDConEtapas(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT c FROM Congreso c LEFT JOIN FETCH c.etapas WHERE c.id = :id",
                    Congreso.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
