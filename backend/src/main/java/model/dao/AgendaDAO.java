package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Agenda;
import model.interfaceDao.IAgendaDAO;

@RequestScoped
public class AgendaDAO extends GenericDAO<Agenda> implements IAgendaDAO {

    @Inject
    public AgendaDAO(EntityManager em) {
        super(em, Agenda.class);
    }

    @Override
    public List<Agenda> getAgendas() {
        return listarTodos();
    }

    @Override
    public void saveAgenda(Agenda entidad) {
        guardar(entidad);
    }

    @Override
    public Agenda modifyAgenda(Agenda entidad) {
        return modificar(entidad);
    }

    @Override
    public Agenda getByIDConItems(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT a FROM Agenda a LEFT JOIN FETCH a.items i LEFT JOIN FETCH i.actividad WHERE a.id = :id",
                    Agenda.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
