package model.interfaceDao;

import java.util.List;
import model.Congreso;

public interface ICongresoDAO extends IGenericDAO<Congreso> {
    List<Congreso> getCongresos();
    void saveCongreso(Congreso entidad);
    Congreso modifyCongreso(Congreso entidad);
    Congreso getByName(String name);
    boolean deleteByName(String name);
    Congreso getByIDConEtapas(Long id);
}
