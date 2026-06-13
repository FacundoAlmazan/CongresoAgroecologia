package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.QR;
import model.interfaceDao.IQRDAO;

@RequestScoped
public class QRDAO extends GenericDAO<QR> implements IQRDAO {

    @Inject
    public QRDAO(EntityManager em) {
        super(em, QR.class);
    }

    @Override
    public List<QR> getQRs() {
        return listarTodos();
    }

    @Override
    public void saveQR(QR entidad) {
        guardar(entidad);
    }

    @Override
    public QR modifyQR(QR entidad) {
        return modificar(entidad);
    }
}
