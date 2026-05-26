package model.interfaceDao;

import java.util.List;
import model.DisponibilidadEvaluadorEje;

public interface IDisponibilidadEvaluadorEjeDAO extends IGenericDAO<DisponibilidadEvaluadorEje> {
    List<DisponibilidadEvaluadorEje> getDisponibilidadesEvaluadorEje();
    void saveDisponibilidadEvaluadorEje(DisponibilidadEvaluadorEje entidad);
    DisponibilidadEvaluadorEje modifyDisponibilidadEvaluadorEje(DisponibilidadEvaluadorEje entidad);
}
