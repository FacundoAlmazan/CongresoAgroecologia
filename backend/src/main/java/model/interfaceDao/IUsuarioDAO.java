package model.interfaceDao;

import java.util.List;

import model.Rol;
import model.Usuario;

public interface IUsuarioDAO {

	List<Usuario> getUsuarios();
	List<Usuario> getUsuariosByRol(Rol r);
	List<Usuario> getUsuariosByName(String name, String ap);

	Usuario getByID(Long id);

	void modifyUsuario(Usuario a);

	boolean deleteByID(Long id);

	void saveUsuario(Usuario a);
	
}