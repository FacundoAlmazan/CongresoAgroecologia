package model.interfaceDao;

import java.util.List;
import model.Notificacion;

public interface INotificacionDAO extends IGenericDAO<Notificacion> {
    List<Notificacion> getNotificaciones();
    void saveNotificacion(Notificacion entidad);
    Notificacion modifyNotificacion(Notificacion entidad);
}
