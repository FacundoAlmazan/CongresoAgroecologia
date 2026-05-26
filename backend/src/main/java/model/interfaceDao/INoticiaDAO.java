package model.interfaceDao;

import java.util.List;
import model.Noticia;

public interface INoticiaDAO extends IGenericDAO<Noticia> {
    List<Noticia> getNoticias();
    void saveNoticia(Noticia entidad);
    Noticia modifyNoticia(Noticia entidad);
}
