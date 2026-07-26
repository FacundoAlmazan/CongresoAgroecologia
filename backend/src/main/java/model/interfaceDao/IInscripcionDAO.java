package model.interfaceDao;

import java.util.List;
import model.Inscripcion;
import model.enums.EstadoPago;

public interface IInscripcionDAO extends IGenericDAO<Inscripcion> {
    List<Inscripcion> getInscripciones();
    void saveInscripcion(Inscripcion entidad);
    Inscripcion modifyInscripcion(Inscripcion entidad);
    List<Inscripcion> getInscripcionesByEstadoPago(EstadoPago p);
    Inscripcion getByIDConDatosFacturaYQR(Long id);
    Inscripcion getUltimaPorUsuario(Long usuarioId);
}
