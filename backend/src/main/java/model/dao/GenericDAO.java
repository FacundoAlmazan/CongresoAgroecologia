package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;

public abstract class GenericDAO<T> {

    protected EntityManager em;
    private Class<T> entityClass;

    public GenericDAO(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public List<T> listarTodos() {
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(jpql, entityClass).getResultList();
    }

    public T getByID(Long id) {
        return em.find(entityClass, id);
    }

    public void guardar(T entidad) {
        em.getTransaction().begin();
        em.persist(entidad);
        em.getTransaction().commit();
    }

    public T modificar(T entidad) {
        em.getTransaction().begin();
        T actualizada = em.merge(entidad);
        em.getTransaction().commit();
        return actualizada;
    }

    public boolean deleteByID(Long id) {
        T entidad = getByID(id);
        if (entidad == null) {
            return false;
        }
        em.getTransaction().begin();
        T gestionada = em.contains(entidad) ? entidad : em.merge(entidad);
        em.remove(gestionada);
        em.getTransaction().commit();
        return true;
    }
}
