package model.interfaceDao;

import java.util.List;
import model.Permiso;

public interface IPermisoDAO {

	List<Permiso> getPermisos();

	Permiso getByID(Long id);
	Permiso getByName(String name);
	void modifyPermiso(Permiso a);

	boolean deleteByID(Long id);
boolean deleteByName(String name);
	void savePermiso(Permiso a);
	
}