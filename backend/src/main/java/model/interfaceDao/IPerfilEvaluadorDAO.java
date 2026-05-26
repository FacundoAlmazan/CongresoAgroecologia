package model.interfaceDao;

import java.util.List;
import model.PerfilEvaluador;

public interface IPerfilEvaluadorDAO extends IGenericDAO<PerfilEvaluador> {
    List<PerfilEvaluador> getPerfilesEvaluador();
    void savePerfilEvaluador(PerfilEvaluador entidad);
    PerfilEvaluador modifyPerfilEvaluador(PerfilEvaluador entidad);
    PerfilEvaluador getByIDConDisponibilidades(Long id);
}
