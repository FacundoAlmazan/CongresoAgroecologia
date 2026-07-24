package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import model.AsignacionEvaluacion;
import model.Evaluacion;
import model.Trabajo;
import model.Usuario;
import model.interfaceDao.IAsignacionEvaluacionDAO;
import model.interfaceDao.IEvaluacionDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;
import java.time.LocalDate;
import java.util.List;

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
    
    @Inject
    private  AsignacionEvaluacionService asignacionService;

    public List<Evaluacion> getEvaluacionesPorTrabajo(Long trabajoId) {
        if (trabajoId == null) {
            throw new IllegalArgumentException("El id del trabajo no puede ser nulo.");
        }
        System.out.println("Hasta aca estamos bien: " +trabajoId);
       
            Trabajo trabajo = trabajoDAO.getByID(trabajoId);
            System.out.println("Llegó a cargar el trabajo");
       
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
    	evaluacion.setTrabajo(trabajoDAO.getByID(evaluacion.getTrabajo().getId()));
        
        AsignacionEvaluacion asg=asignacionDAO.getPorTrabajoYEvaluador(evaluacion.getTrabajo().getId(),evaluacion.getEvaluador().getId());
        if (asg == null) {
            throw new IllegalArgumentException("El trabajo no está asignado al evaluador.");
        }
        
        evaluacion.setAsignacion(asg);
        evaluacion.setFecha(LocalDate.now());
         evaluacionDAO.saveEvaluacion(evaluacion);
         
         
    }
}