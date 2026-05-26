package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.DatosFactura;
import model.interfaceDao.IDatosFacturaDAO;

public class DatosFacturaDAO extends GenericDAO<DatosFactura> implements IDatosFacturaDAO {

    public DatosFacturaDAO(EntityManager em) {
        super(em, DatosFactura.class);
    }

    @Override
    public List<DatosFactura> getDatosFactura() {
        return listarTodos();
    }

    @Override
    public void saveDatosFactura(DatosFactura entidad) {
        guardar(entidad);
    }

    @Override
    public DatosFactura modifyDatosFactura(DatosFactura entidad) {
        return modificar(entidad);
    }
}
