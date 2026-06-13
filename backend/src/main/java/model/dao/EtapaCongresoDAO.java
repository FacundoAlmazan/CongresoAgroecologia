package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.EtapaCongreso;
import model.interfaceDao.IEtapaCongresoDAO;

@RequestScoped
public class EtapaCongresoDAO extends GenericDAO<EtapaCongreso> implements IEtapaCongresoDAO {

    @Inject
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
