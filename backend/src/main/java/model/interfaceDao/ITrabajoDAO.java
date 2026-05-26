package model.interfaceDao;

import java.util.List;
import model.Trabajo;
import model.enums.EstadoTrabajo;
import model.enums.TipoTrabajo;

public interface ITrabajoDAO extends IGenericDAO<Trabajo> {
    List<Trabajo> getTrabajos();
    void saveTrabajo(Trabajo entidad);
    Trabajo modifyTrabajo(Trabajo entidad);
    List<Trabajo> getByTipoTrabajo(TipoTrabajo tt);
    List<Trabajo> getByEstadoTrabajo(EstadoTrabajo et);
    Trabajo getByIDConAutoresYEjes(Long id);
}
