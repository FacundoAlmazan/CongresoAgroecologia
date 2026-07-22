package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import model.Evaluacion;
import model.Trabajo;
import model.Usuario;
import model.interfaceDao.IEvaluacionDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;

import java.util.List;

@RequestScoped
public class EvaluacionService {

    @Inject
    private IEvaluacionDAO evaluacionDAO;

    @Inject
    private ITrabajoDAO trabajoDAO;

    @Inject
    private IUsuarioDAO usuarioDAO;

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
    
    public void guardarEvaluacion(Evaluacion evaluacion) {

        if (evaluacion == null) {
            throw new IllegalArgumentException("La evaluación no puede ser nula.");
        }

        if (evaluacion.getTrabajo() == null) {
            throw new IllegalArgumentException("La evaluación debe estar asociada a un trabajo.");
        }

        evaluacionDAO.saveEvaluacion(evaluacion);
    }
}