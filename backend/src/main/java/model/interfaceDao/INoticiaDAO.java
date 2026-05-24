package model.interfaceDao;

import java.util.List;
import model.Noticia;

public interface INoticiaDAO {

	List<Noticia> getNoticias();

	Noticia getByID(Long id);

	void modifyNoticia(Noticia a);

	boolean deleteByID(Long id);

	void saveNoticia(Noticia a);
	
}