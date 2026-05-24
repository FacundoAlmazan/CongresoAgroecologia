package model.interfaceDao;
import java.util.List;
import model.Actividad;

public interface IActividadDAO {
public List<Actividad> getActividades();
public Actividad getByID(Long id);
public void saveActividad(Actividad a);
public void modifyActividad(Actividad a );
public Actividad getByName(String name);
public boolean deleteByName(String name);
public boolean deleteByID(Long id);
}
