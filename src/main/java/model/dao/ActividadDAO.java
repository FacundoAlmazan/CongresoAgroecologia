package model.dao;

import jakarta.persistence.EntityManager;
import model.interfaceDao.IActividadDAO;

public class ActividadDAO implements IActividadDAO {


	public ActividadDAO() {}
	
	public ActividadDAO(EntityManager em) {}

}


