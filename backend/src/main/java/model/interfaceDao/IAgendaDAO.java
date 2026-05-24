package model.interfaceDao;

import java.util.List;

import model.Agenda;

public interface IAgendaDAO {

	List<Agenda> getAgendas();
	Agenda getByID(Long id);
	void modifyAgenda(Agenda a);
	boolean deleteByID(Long id);
	void saveAgenda(Agenda a);
}
