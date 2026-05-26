package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.AutorTrabajo;
import model.interfaceDao.IAutorTrabajoDAO;

public class AutorTrabajoDAO extends GenericDAO<AutorTrabajo> implements IAutorTrabajoDAO {

    public AutorTrabajoDAO(EntityManager em) {
        super(em, AutorTrabajo.class);
    }

    @Override
    public List<AutorTrabajo> getAutoresTrabajo() {
        return listarTodos();
    }

    @Override
    public void saveAutorTrabajo(AutorTrabajo entidad) {
        guardar(entidad);
    }

    @Override
    public AutorTrabajo modifyAutorTrabajo(AutorTrabajo entidad) {
        return modificar(entidad);
    }
}
