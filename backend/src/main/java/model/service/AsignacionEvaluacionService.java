package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
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

    public AsignacionEvaluacion obtener(Long id) {
        AsignacionEvaluacion asignacion = asignacionDAO.getByID(id);
        if (asignacion == null) {
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

    public AsignacionEvaluacion aceptar(Long id) {
        AsignacionEvaluacion asignacion = obtener(id);
        asignacion.setEstado(EstadoAsignacion.ACEPTADA);
        return asignacionDAO.modificar(asignacion);
    }

    public AsignacionEvaluacion rechazar(Long id) {
        AsignacionEvaluacion asignacion = obtener(id);
        asignacion.setEstado(EstadoAsignacion.RECHAZADA);
        return asignacionDAO.modificar(asignacion);
    }
}
