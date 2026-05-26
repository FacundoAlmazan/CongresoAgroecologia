package model.interfaceDao;

import java.util.List;
import model.Rol;
import model.enums.TipoRol;

public interface IRolDAO extends IGenericDAO<Rol> {
    List<Rol> getRoles();
    void saveRol(Rol entidad);
    Rol modifyRol(Rol entidad);
    Rol getByNombre(TipoRol nombre);
}
