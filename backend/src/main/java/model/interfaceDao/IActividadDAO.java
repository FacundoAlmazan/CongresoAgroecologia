package model.interfaceDao;

import java.util.List;
import model.Actividad;

public interface IActividadDAO extends IGenericDAO<Actividad> {
    List<Actividad> getActividades();
    void saveActividad(Actividad entidad);
    Actividad modifyActividad(Actividad entidad);
    Actividad getByName(String name);
    boolean deleteByName(String name);
}
