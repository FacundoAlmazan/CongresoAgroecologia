package model.interfaceDao;

import java.util.List;
import model.PlantillaEmail;

public interface IPlantillaEmailDAO {

	List<PlantillaEmail> getPlantillaEmails();

	PlantillaEmail getByID(Long id);

	void modifyPlantillaEmail(PlantillaEmail a);

	boolean deleteByID(Long id);

	void savePlantillaEmail(PlantillaEmail a);
	
}