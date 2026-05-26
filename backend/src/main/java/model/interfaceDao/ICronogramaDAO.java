package model.interfaceDao;

import java.util.List;
import model.Cronograma;

public interface ICronogramaDAO extends IGenericDAO<Cronograma> {
    List<Cronograma> getCronogramas();
    void saveCronograma(Cronograma entidad);
    Cronograma modifyCronograma(Cronograma entidad);
    Cronograma getByIDConActividades(Long id);
}
