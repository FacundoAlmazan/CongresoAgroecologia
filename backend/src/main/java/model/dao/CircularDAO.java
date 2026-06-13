package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Circular;
import model.interfaceDao.ICircularDAO;

@RequestScoped
public class CircularDAO extends GenericDAO<Circular> implements ICircularDAO {

    @Inject
    public CircularDAO(EntityManager em) {
        super(em, Circular.class);
    }

    @Override
    public List<Circular> getCirculares() {
        return listarTodos();
    }

    @Override
    public void saveCircular(Circular entidad) {
        guardar(entidad);
    }

    @Override
    public Circular modifyCircular(Circular entidad) {
        return modificar(entidad);
    }
}
