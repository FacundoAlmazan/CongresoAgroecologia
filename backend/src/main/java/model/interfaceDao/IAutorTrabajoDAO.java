package model.interfaceDao;

import java.util.List;
import model.AutorTrabajo;

public interface IAutorTrabajoDAO extends IGenericDAO<AutorTrabajo> {
    List<AutorTrabajo> getAutoresTrabajo();
    void saveAutorTrabajo(AutorTrabajo entidad);
    AutorTrabajo modifyAutorTrabajo(AutorTrabajo entidad);
}
