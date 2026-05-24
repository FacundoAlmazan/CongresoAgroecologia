package model.interfaceDao;

import java.util.List;
import model.ItemAgenda;

public interface IItemAgendaDAO {

	List<ItemAgenda> getItemsAgenda();

	ItemAgenda getByID(Long id);

	void modifyItemAgenda(ItemAgenda a);

	boolean deleteByID(Long id);

	void saveItemAgenda(ItemAgenda a);
	
}