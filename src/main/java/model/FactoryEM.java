package model;
import jakarta.persistence.*;
public class FactoryEM {
	private static EntityManagerFactory emf=Persistence.createEntityManagerFactory("nombreUnidaddePersistencia");

	public static EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
	
	
}
