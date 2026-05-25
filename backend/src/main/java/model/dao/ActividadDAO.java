package model.dao;

import jakarta.persistence.EntityManager;
import model.interfaceDao.IActividadDAO;

public abstract class ActividadDAO implements IActividadDAO {


	public ActividadDAO() {}
	
	public ActividadDAO(EntityManager em) {}

}


