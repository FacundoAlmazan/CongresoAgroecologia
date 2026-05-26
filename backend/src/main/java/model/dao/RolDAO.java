package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Rol;
import model.interfaceDao.IRolDAO;
import model.enums.TipoRol;

public class RolDAO extends GenericDAO<Rol> implements IRolDAO {

    public RolDAO(EntityManager em) {
        super(em, Rol.class);
    }

    @Override
    public List<Rol> getRoles() {
        return listarTodos();
    }

    @Override
    public void saveRol(Rol entidad) {
        guardar(entidad);
    }

    @Override
    public Rol modifyRol(Rol entidad) {
        return modificar(entidad);
    }

    @Override
    public Rol getByNombre(TipoRol nombre) {
        try {
            return em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre", Rol.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
