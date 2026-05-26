package model.interfaceDao;

import java.util.List;
import model.EjeTematico;

public interface IEjeTematicoDAO extends IGenericDAO<EjeTematico> {
    List<EjeTematico> getEjesTematicos();
    void saveEjeTematico(EjeTematico entidad);
    EjeTematico modifyEjeTematico(EjeTematico entidad);
    EjeTematico getByNombre(String nombre);
    EjeTematico getByIDConTrabajos(Long id);
}
