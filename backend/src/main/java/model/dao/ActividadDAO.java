package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Actividad;
import model.interfaceDao.IActividadDAO;

@RequestScoped
public class ActividadDAO extends GenericDAO<Actividad> implements IActividadDAO {

    @Inject
    public ActividadDAO(EntityManager em) {
        super(em, Actividad.class);
    }

    @Override
    public List<Actividad> getActividades() {
        return listarTodos();
    }

    @Override
    public void saveActividad(Actividad entidad) {
        guardar(entidad);
    }

    @Override
    public Actividad modifyActividad(Actividad entidad) {
        return modificar(entidad);
    }

    @Override
    public Actividad getByName(String name) {
        try {
            return em.createQuery("SELECT a FROM Actividad a WHERE a.titulo = :name", Actividad.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        Actividad actividad = getByName(name);
        return actividad != null && deleteByID(actividad.getId());
    }
}
