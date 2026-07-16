package model.interfaceDao;

import java.util.List;
import model.Usuario;
import model.Rol;

public interface IUsuarioDAO extends IGenericDAO<Usuario> {
    List<Usuario> getUsuarios();
    void saveUsuario(Usuario entidad);
    Usuario modifyUsuario(Usuario entidad);
    List<Usuario> getUsuariosByRol(Rol r);
    List<Usuario> getUsuariosByName(String name, String ap);
    Usuario getByEmail(String email);
    Usuario getByIDConRoles(Long id);
    Usuario getByNumeroIdentificacionConRoles(String numeroIdentificacion);
}
