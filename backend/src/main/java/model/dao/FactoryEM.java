package model.dao;

import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class FactoryEM {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("miUP");

    /**
     * Método viejo, se deja para compatibilidad con código anterior.
     * En la entrega REST/CDI, la idea es NO llamarlo desde los Resources/DAOs,
     * sino usar @Inject EntityManager.
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Productor CDI: cuando una clase pida @Inject EntityManager,
     * CDI usa este método para crear uno por request HTTP.
     */
    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * CDI llama a este método al finalizar el request para cerrar el EntityManager.
     */
    public void closeEntityManager(@Disposes EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @PreDestroy
    public void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
