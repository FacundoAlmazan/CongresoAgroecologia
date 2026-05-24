package model.interfaceDao;

import java.util.List;
import model.Congreso;

public interface ICongresoDAO {

	List<Congreso> getCongresos();

	Congreso getByID(Long id);
	Congreso getByName(String name);

	void modifyCongreso(Congreso a);

	boolean deleteByID(Long id);
	boolean  deleteByName(String name);


	void saveCongreso(Congreso a);
	
}