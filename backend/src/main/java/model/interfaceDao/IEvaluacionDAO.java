package model.interfaceDao;

import java.util.List;
import model.Evaluacion;

public interface IEvaluacionDAO extends IGenericDAO<Evaluacion> {
    List<Evaluacion> getEvaluaciones();
    void saveEvaluacion(Evaluacion entidad);
    Evaluacion modifyEvaluacion(Evaluacion entidad);
    List<Evaluacion> getEvaluacionesPorTrabajo(Long trabajoId);
    List<Evaluacion> getEvaluacionesPorEvaluador(Long trabajoId);
}
