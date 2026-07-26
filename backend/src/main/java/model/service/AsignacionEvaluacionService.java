package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import model.AsignacionEvaluacion;
import model.enums.EstadoAsignacion;
import model.interfaceDao.IAsignacionEvaluacionDAO;

@RequestScoped
public class AsignacionEvaluacionService {

    @Inject
    private IAsignacionEvaluacionDAO asignacionDAO;

    public List<AsignacionEvaluacion> listar() {
        return asignacionDAO.listarTodos();
    }

    public List<AsignacionEvaluacion> listarPorEvaluador(Long evaluadorId) {
        if (evaluadorId == null) {
            throw new BadRequestException("No se pudo identificar al evaluador.");
        }
        return asignacionDAO.getPorEvaluador(evaluadorId);
    }

    public AsignacionEvaluacion obtener(Long id) {
        AsignacionEvaluacion asignacion = asignacionDAO.getByID(id);
        if (asignacion == null) {
            throw new NotFoundException("Asignación no encontrada con id " + id);
        }
        return asignacion;
    }

    public AsignacionEvaluacion obtenerPorTrabajoYEvaluador(Long trabajoId, Long evaluadorId) {
        AsignacionEvaluacion asignacion =
                asignacionDAO.getPorTrabajoYEvaluador(trabajoId, evaluadorId);
        if (asignacion == null) {
            throw new NotFoundException(
                    "No se encontró una asignación para el trabajo "
                            + trabajoId + " y el evaluador " + evaluadorId);
        }
        return asignacion;
    }

    public AsignacionEvaluacion obtenerParaEvaluador(Long id, Long evaluadorId) {
        if (evaluadorId == null) {
            throw new BadRequestException("No se pudo identificar al evaluador.");
        }
        AsignacionEvaluacion asignacion = obtener(id);
        if (asignacion.getEvaluador() == null
                || !evaluadorId.equals(asignacion.getEvaluador().getId())) {
            throw new NotFoundException("Asignación no encontrada con id " + id);
        }
        return asignacion;
    }

    public AsignacionEvaluacion crear(AsignacionEvaluacion asignacion) {
        asignacionDAO.guardar(asignacion);
        return asignacion;
    }

    public AsignacionEvaluacion modificar(Long id, AsignacionEvaluacion asignacion) {
        obtener(id);
        asignacion.setId(id);
        return asignacionDAO.modificar(asignacion);
    }

    public void eliminar(Long id) {
        if (!asignacionDAO.deleteByID(id)) {
            throw new NotFoundException("Asignación no encontrada con id " + id);
        }
    }

    public AsignacionEvaluacion aceptar(Long id, Long evaluadorId) {
        AsignacionEvaluacion asignacion = obtenerParaEvaluador(id, evaluadorId);
        validarPendiente(asignacion);
        asignacion.setEstado(EstadoAsignacion.ACEPTADA);
        return asignacionDAO.modificar(asignacion);
    }

    public AsignacionEvaluacion rechazar(Long id, Long evaluadorId) {
        AsignacionEvaluacion asignacion = obtenerParaEvaluador(id, evaluadorId);
        validarPendiente(asignacion);
        asignacion.setEstado(EstadoAsignacion.RECHAZADA);
        return asignacionDAO.modificar(asignacion);
    }

    private void validarPendiente(AsignacionEvaluacion asignacion) {
        if (asignacion.getEstado() != EstadoAsignacion.PENDIENTE) {
            throw new BadRequestException("La asignación ya fue respondida.");
        }
    }
}
