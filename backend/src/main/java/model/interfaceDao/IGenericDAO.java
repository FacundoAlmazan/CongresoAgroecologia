package model.interfaceDao;

import java.util.List;

public interface IGenericDAO<T> {
    List<T> listarTodos();
    T getByID(Long id);
    void guardar(T entidad);
    T modificar(T entidad);
    boolean deleteByID(Long id);
}
