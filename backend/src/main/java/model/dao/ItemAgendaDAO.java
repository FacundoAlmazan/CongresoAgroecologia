package model.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.ItemAgenda;
import model.interfaceDao.IItemAgendaDAO;

public class ItemAgendaDAO extends GenericDAO<ItemAgenda> implements IItemAgendaDAO {

    public ItemAgendaDAO(EntityManager em) {
        super(em, ItemAgenda.class);
    }

    @Override
    public List<ItemAgenda> getItemsAgenda() {
        return listarTodos();
    }

    @Override
    public void saveItemAgenda(ItemAgenda entidad) {
        guardar(entidad);
    }

    @Override
    public ItemAgenda modifyItemAgenda(ItemAgenda entidad) {
        return modificar(entidad);
    }
}
