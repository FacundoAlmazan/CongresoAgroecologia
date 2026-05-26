package model.interfaceDao;

import java.util.List;
import model.Agenda;

public interface IAgendaDAO extends IGenericDAO<Agenda> {
    List<Agenda> getAgendas();
    void saveAgenda(Agenda entidad);
    Agenda modifyAgenda(Agenda entidad);
    Agenda getByIDConItems(Long id);
}
