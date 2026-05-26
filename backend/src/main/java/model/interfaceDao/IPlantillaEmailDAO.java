package model.interfaceDao;

import java.util.List;
import model.PlantillaEmail;

public interface IPlantillaEmailDAO extends IGenericDAO<PlantillaEmail> {
    List<PlantillaEmail> getPlantillaEmails();
    void savePlantillaEmail(PlantillaEmail entidad);
    PlantillaEmail modifyPlantillaEmail(PlantillaEmail entidad);
}
