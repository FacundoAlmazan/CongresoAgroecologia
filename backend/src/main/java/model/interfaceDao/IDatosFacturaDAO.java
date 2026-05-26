package model.interfaceDao;

import java.util.List;
import model.DatosFactura;

public interface IDatosFacturaDAO extends IGenericDAO<DatosFactura> {
    List<DatosFactura> getDatosFactura();
    void saveDatosFactura(DatosFactura entidad);
    DatosFactura modifyDatosFactura(DatosFactura entidad);
}
