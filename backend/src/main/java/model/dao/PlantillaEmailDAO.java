package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.PlantillaEmail;
import model.interfaceDao.IPlantillaEmailDAO;

public class PlantillaEmailDAO extends GenericDAO<PlantillaEmail> implements IPlantillaEmailDAO {

    public PlantillaEmailDAO(EntityManager em) {
        super(em, PlantillaEmail.class);
    }

    @Override
    public List<PlantillaEmail> getPlantillaEmails() {
        return listarTodos();
    }

    @Override
    public void savePlantillaEmail(PlantillaEmail entidad) {
        guardar(entidad);
    }

    @Override
    public PlantillaEmail modifyPlantillaEmail(PlantillaEmail entidad) {
        return modificar(entidad);
    }
}
