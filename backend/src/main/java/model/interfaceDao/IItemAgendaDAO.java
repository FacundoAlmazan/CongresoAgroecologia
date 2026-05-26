package model.interfaceDao;

import java.util.List;
import model.ItemAgenda;

public interface IItemAgendaDAO extends IGenericDAO<ItemAgenda> {
    List<ItemAgenda> getItemsAgenda();
    void saveItemAgenda(ItemAgenda entidad);
    ItemAgenda modifyItemAgenda(ItemAgenda entidad);
}
