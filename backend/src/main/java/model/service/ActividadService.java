package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import model.Actividad;
import model.interfaceDao.IActividadDAO;

@RequestScoped
public class ActividadService {

    @Inject
    private IActividadDAO actividadDAO;

    public List<Actividad> listar() {
        return actividadDAO.listarTodos();
    }

    public Actividad obtener(Long id) {
        Actividad actividad = actividadDAO.getByID(id);
        if (actividad == null) {
            throw new NotFoundException("Actividad no encontrada con id " + id);
        }
        return actividad;
    }

    public Actividad crear(Actividad actividad) {
        validarActividad(actividad);
        validarConflicto(actividad, null);
        actividadDAO.guardar(actividad);
        return actividad;
    }

    public Actividad modificar(Long id, Actividad actividad) {
        obtener(id);
        actividad.setId(id);
        validarActividad(actividad);
        validarConflicto(actividad, id);
        return actividadDAO.modificar(actividad);
    }

    public void eliminar(Long id) {
        if (!actividadDAO.deleteByID(id)) {
            throw new NotFoundException("Actividad no encontrada con id " + id);
        }
    }

    private void validarActividad(Actividad actividad) {
        if (actividad == null) {
            throw new BadRequestException("Debe enviar una actividad");
        }
        if (actividad.getHoraInicio() == null || actividad.getHoraFin() == null || !actividad.getHoraInicio().isBefore(actividad.getHoraFin())) {
            throw new BadRequestException("El horario de la actividad es inválido");
        }
    }

    private void validarConflicto(Actividad actividad, Long idIgnorado) {
        for (Actividad existente : actividadDAO.listarTodos()) {
            if (idIgnorado != null && idIgnorado.equals(existente.getId())) {
                continue;
            }
            if (actividad.seSuperponeCon(existente)) {
                throw new BadRequestException("Existe conflicto de horario y aula con la actividad id " + existente.getId());
            }
        }
    }
}
