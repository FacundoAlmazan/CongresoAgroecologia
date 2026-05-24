package model.dao;

import java.util.List;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.FactoryEM;


public abstract class DAO<T> {

    private final Class<T> entityClass;

    //Recupero la clase del invocador
    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> getList() {
    	try (EntityManager em=FactoryEM.getEntityManager()){
    	TypedQuery<T> query = em.createQuery("SELECT c FROM  "+entityClass.getName()+" c", entityClass);
    	
        return query.getResultList(); 
    	}
    }

    public T getByID(Long id) {
        try(EntityManager em=FactoryEM.getEntityManager()){
        	return em.find(entityClass, id);
        }
    }

    public void save(T entity) {
        EntityTransaction tr = null;
        try (EntityManager em = FactoryEM.getEntityManager()) {
            tr = em.getTransaction();
            tr.begin();
            em.merge(entity); 
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback(); 
            }
            System.err.println("Error al persistir la entidad: " + e.getMessage());
        }
    }

    public void delete(T entity) {
        EntityTransaction tr = null;
        try (EntityManager em = FactoryEM.getEntityManager()) {
            tr = em.getTransaction();
            tr.begin();
            em.remove(em.merge(entity));
            tr.commit();
        } catch (Exception e) {
            if (tr != null && tr.isActive()) {
                tr.rollback(); 
            }
            System.err.println("Error al eliminar la entidad: " + e.getMessage());
        }
    }
}