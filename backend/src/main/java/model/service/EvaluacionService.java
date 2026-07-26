package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import model.AsignacionEvaluacion;
import model.Evaluacion;
import model.Trabajo;
import model.Usuario;
import model.enums.EstadoAsignacion;
import model.interfaceDao.IAsignacionEvaluacionDAO;
import model.interfaceDao.IEvaluacionDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;

@RequestScoped
public class EvaluacionService {

    @Inject
    private IEvaluacionDAO evaluacionDAO;

    @Inject
    private ITrabajoDAO trabajoDAO;

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private IAsignacionEvaluacionDAO asignacionDAO;

    public List<Evaluacion> getEvaluacionesPorTrabajo(Long trabajoId) {
        if (trabajoId == null) {
            throw new IllegalArgumentException("El id del trabajo no puede ser nulo.");
        }

        Trabajo trabajo = trabajoDAO.getByID(trabajoId);
        if (trabajo == null) {
            throw new IllegalArgumentException("El trabajo no existe.");
        }
        return evaluacionDAO.getEvaluacionesPorTrabajo(trabajoId);
    }

    public List<Evaluacion> getEvaluacionesPorEvaluador(Long evaluadorId) {
        if (evaluadorId == null) {
            throw new IllegalArgumentException("El id del evaluador no puede ser nulo.");
        }
        Usuario evaluador = usuarioDAO.getByID(evaluadorId);
        if (evaluador == null) {
            throw new IllegalArgumentException("El evaluador no existe.");
        }
        return evaluacionDAO.getEvaluacionesPorEvaluador(evaluadorId);
    }

    public Evaluacion guardarEvaluacion(Evaluacion evaluacion, Long evaluadorId) {
        if (evaluacion == null) {
            throw new BadRequestException("La evaluación no puede ser nula.");
        }
        if (evaluacion.getTrabajo() == null || evaluacion.getTrabajo().getId() == null) {
            throw new BadRequestException(
                    "La evaluación debe estar asociada a un trabajo válido.");
        }
        if (evaluacion.getRecomendacion() == null) {
            throw new BadRequestException("La recomendación es obligatoria.");
        }
        if (evaluadorId == null) {
            throw new BadRequestException("No se pudo identificar al evaluador.");
        }

        Long trabajoId = evaluacion.getTrabajo().getId();
        Trabajo trabajo = trabajoDAO.getByID(trabajoId);
        if (trabajo == null) {
            throw new NotFoundException("Trabajo no encontrado con id " + trabajoId);
        }

        Usuario evaluador = usuarioDAO.getByID(evaluadorId);
        if (evaluador == null) {
            throw new NotFoundException("Evaluador no encontrado con id " + evaluadorId);
        }

        AsignacionEvaluacion asignacion =
                asignacionDAO.getPorTrabajoYEvaluador(trabajoId, evaluadorId);
        if (asignacion == null) {
            throw new BadRequestException("El trabajo no está asignado al evaluador.");
        }
        if (asignacion.getEstado() != EstadoAsignacion.ACEPTADA) {
            throw new BadRequestException(
                    "La asignación debe estar aceptada antes de evaluar el trabajo.");
        }
        if (asignacion.isFinalizado()) {
            throw new BadRequestException("La asignación ya fue evaluada.");
        }

        evaluacion.setId(null);
        evaluacion.setTrabajo(trabajo);
        evaluacion.setEvaluador(evaluador);
        evaluacion.setAsignacion(asignacion);
        evaluacion.setFecha(LocalDate.now());
        evaluacionDAO.saveEvaluacion(evaluacion);
        return evaluacion;
    }
}
