package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.DisponibilidadEvaluadorEje;
import model.interfaceDao.IDisponibilidadEvaluadorEjeDAO;

@RequestScoped
public class DisponibilidadEvaluadorEjeDAO extends GenericDAO<DisponibilidadEvaluadorEje> implements IDisponibilidadEvaluadorEjeDAO {

    @Inject
    public DisponibilidadEvaluadorEjeDAO(EntityManager em) {
        super(em, DisponibilidadEvaluadorEje.class);
    }

    @Override
    public List<DisponibilidadEvaluadorEje> getDisponibilidadesEvaluadorEje() {
        return listarTodos();
    }

    @Override
    public void saveDisponibilidadEvaluadorEje(DisponibilidadEvaluadorEje entidad) {
        guardar(entidad);
    }

    @Override
    public DisponibilidadEvaluadorEje modifyDisponibilidadEvaluadorEje(DisponibilidadEvaluadorEje entidad) {
        return modificar(entidad);
    }
}
