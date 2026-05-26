package model.interfaceDao;

import java.util.List;
import model.Circular;

public interface ICircularDAO extends IGenericDAO<Circular> {
    List<Circular> getCirculares();
    void saveCircular(Circular entidad);
    Circular modifyCircular(Circular entidad);
}
