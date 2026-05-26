package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.EtapaCongreso;
import model.interfaceDao.IEtapaCongresoDAO;

public class EtapaCongresoDAO extends GenericDAO<EtapaCongreso> implements IEtapaCongresoDAO {

    public EtapaCongresoDAO(EntityManager em) {
        super(em, EtapaCongreso.class);
    }

    @Override
    public List<EtapaCongreso> getEtapaCongresos() {
        return listarTodos();
    }

    @Override
    public void saveEtapaCongreso(EtapaCongreso entidad) {
        guardar(entidad);
    }

    @Override
    public EtapaCongreso modifyEtapaCongreso(EtapaCongreso entidad) {
        return modificar(entidad);
    }
}
