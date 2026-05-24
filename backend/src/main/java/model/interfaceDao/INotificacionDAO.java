package model.interfaceDao;

import java.util.List;
import model.Notificacion;

public interface INotificacionDAO {

	List<Notificacion> getNotificaciones();

	Notificacion getByID(Long id);

	void modifyNotificacion(Notificacion a);

	boolean deleteByID(Long id);

	void saveNotificacion(Notificacion a);
	
}