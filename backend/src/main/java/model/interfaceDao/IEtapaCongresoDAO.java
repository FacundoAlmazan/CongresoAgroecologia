package model.interfaceDao;

import java.util.List;
import model.EtapaCongreso;

public interface IEtapaCongresoDAO extends IGenericDAO<EtapaCongreso> {
    List<EtapaCongreso> getEtapaCongresos();
    void saveEtapaCongreso(EtapaCongreso entidad);
    EtapaCongreso modifyEtapaCongreso(EtapaCongreso entidad);
}
