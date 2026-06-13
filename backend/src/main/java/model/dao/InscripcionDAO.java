package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Inscripcion;
import model.interfaceDao.IInscripcionDAO;
import model.enums.EstadoPago;

@RequestScoped
public class InscripcionDAO extends GenericDAO<Inscripcion> implements IInscripcionDAO {

    @Inject
    public InscripcionDAO(EntityManager em) {
        super(em, Inscripcion.class);
    }

    @Override
    public List<Inscripcion> getInscripciones() {
        return listarTodos();
    }

    @Override
    public void saveInscripcion(Inscripcion entidad) {
        guardar(entidad);
    }

    @Override
    public Inscripcion modifyInscripcion(Inscripcion entidad) {
        return modificar(entidad);
    }

    @Override
    public List<Inscripcion> getInscripcionesByEstadoPago(EstadoPago p) {
        return em.createQuery("SELECT i FROM Inscripcion i WHERE i.estado = :estado", Inscripcion.class)
                .setParameter("estado", p)
                .getResultList();
    }

    @Override
    public Inscripcion getByIDConDatosFacturaYQR(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT i FROM Inscripcion i " +
                    "LEFT JOIN FETCH i.datosFactura " +
                    "LEFT JOIN FETCH i.qr " +
                    "WHERE i.id = :id",
                    Inscripcion.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
