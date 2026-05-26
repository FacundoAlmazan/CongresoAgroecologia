package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.EjeTematico;
import model.interfaceDao.IEjeTematicoDAO;

public class EjeTematicoDAO extends GenericDAO<EjeTematico> implements IEjeTematicoDAO {

    public EjeTematicoDAO(EntityManager em) {
        super(em, EjeTematico.class);
    }

    @Override
    public List<EjeTematico> getEjesTematicos() {
        return listarTodos();
    }

    @Override
    public void saveEjeTematico(EjeTematico entidad) {
        guardar(entidad);
    }

    @Override
    public EjeTematico modifyEjeTematico(EjeTematico entidad) {
        return modificar(entidad);
    }

    @Override
    public EjeTematico getByNombre(String nombre) {
        try {
            return em.createQuery("SELECT e FROM EjeTematico e WHERE e.nombre = :nombre", EjeTematico.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public EjeTematico getByIDConTrabajos(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT e FROM EjeTematico e LEFT JOIN FETCH e.trabajos WHERE e.id = :id",
                    EjeTematico.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
