package model.interfaceDao;

import java.util.List;
import model.AsignacionEvaluacion;
import model.enums.EstadoAsignacion;

public interface IAsignacionEvaluacionDAO extends IGenericDAO<AsignacionEvaluacion> {
    List<AsignacionEvaluacion> getAsignacionesEvaluacion();
    void saveAsignacionEvaluacion(AsignacionEvaluacion entidad);
    AsignacionEvaluacion modifyAsignacionEvaluacion(AsignacionEvaluacion entidad);
    List<AsignacionEvaluacion> getByEstado(EstadoAsignacion estado);
}
