package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Notificacion;
import model.interfaceDao.INotificacionDAO;

public class NotificacionDAO extends GenericDAO<Notificacion> implements INotificacionDAO {

    public NotificacionDAO(EntityManager em) {
        super(em, Notificacion.class);
    }

    @Override
    public List<Notificacion> getNotificaciones() {
        return listarTodos();
    }

    @Override
    public void saveNotificacion(Notificacion entidad) {
        guardar(entidad);
    }

    @Override
    public Notificacion modifyNotificacion(Notificacion entidad) {
        return modificar(entidad);
    }
}
