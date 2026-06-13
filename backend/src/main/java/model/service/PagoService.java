package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import model.Inscripcion;
import model.Usuario;
import model.interfaceDao.IInscripcionDAO;
import model.interfaceDao.IUsuarioDAO;

@RequestScoped
public class PagoService {

    @Inject
    private IInscripcionDAO inscripcionDAO;

    @Inject
    private IUsuarioDAO usuarioDAO;

    public List<Inscripcion> listar() {
        return inscripcionDAO.listarTodos();
    }

    public Inscripcion obtener(Long id) {
        Inscripcion inscripcion = inscripcionDAO.getByID(id);
        if (inscripcion == null) {
            throw new NotFoundException("Pago/inscripción no encontrado con id " + id);
        }
        return inscripcion;
    }

    public Inscripcion registrar(Inscripcion inscripcion) {
        if (inscripcion == null) {
            throw new BadRequestException("Debe enviar los datos del pago");
        }
        inscripcionDAO.guardar(inscripcion);
        return inscripcion;
    }

    public Inscripcion aprobar(Long id, Long adminId) {
        Inscripcion inscripcion = obtener(id);
        Usuario admin = obtenerUsuario(adminId);
        inscripcion.aprobar(admin);
        return inscripcionDAO.modificar(inscripcion);
    }

    public Inscripcion rechazar(Long id, Long adminId, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new BadRequestException("Debe indicar el motivo de rechazo");
        }
        Inscripcion inscripcion = obtener(id);
        Usuario admin = obtenerUsuario(adminId);
        inscripcion.rechazar(admin, motivo);
        return inscripcionDAO.modificar(inscripcion);
    }

    private Usuario obtenerUsuario(Long id) {
        if (id == null) {
            throw new BadRequestException("Debe indicar el usuario administrador");
        }
        Usuario usuario = usuarioDAO.getByID(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        return usuario;
    }
}
