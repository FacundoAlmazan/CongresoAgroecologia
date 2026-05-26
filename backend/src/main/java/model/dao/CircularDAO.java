package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Circular;
import model.interfaceDao.ICircularDAO;

public class CircularDAO extends GenericDAO<Circular> implements ICircularDAO {

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
