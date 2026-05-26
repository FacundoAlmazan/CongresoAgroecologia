package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Noticia;
import model.interfaceDao.INoticiaDAO;

public class NoticiaDAO extends GenericDAO<Noticia> implements INoticiaDAO {

    public NoticiaDAO(EntityManager em) {
        super(em, Noticia.class);
    }

    @Override
    public List<Noticia> getNoticias() {
        return listarTodos();
    }

    @Override
    public void saveNoticia(Noticia entidad) {
        guardar(entidad);
    }

    @Override
    public Noticia modifyNoticia(Noticia entidad) {
        return modificar(entidad);
    }
}
