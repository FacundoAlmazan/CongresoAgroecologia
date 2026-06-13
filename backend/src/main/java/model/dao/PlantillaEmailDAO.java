package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.PlantillaEmail;
import model.interfaceDao.IPlantillaEmailDAO;

@RequestScoped
public class PlantillaEmailDAO extends GenericDAO<PlantillaEmail> implements IPlantillaEmailDAO {

    @Inject
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
