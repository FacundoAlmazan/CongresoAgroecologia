package model.dao;

import jakarta.persistence.EntityManager;
import model.interfaceDao.*;

public class FactoryDAO {

    private EntityManager em;

    public FactoryDAO(EntityManager em) {
        this.em = em;
    }

    public IActividadDAO getActividadDAO() {
        return new ActividadDAO(em);
    }

    public IAgendaDAO getAgendaDAO() {
        return new AgendaDAO(em);
    }

    public IAsignacionEvaluacionDAO getAsignacionEvaluacionDAO() {
        return new AsignacionEvaluacionDAO(em);
    }

    public IAutorTrabajoDAO getAutorTrabajoDAO() {
        return new AutorTrabajoDAO(em);
    }

    public ICircularDAO getCircularDAO() {
        return new CircularDAO(em);
    }

    public ICongresoDAO getCongresoDAO() {
        return new CongresoDAO(em);
    }

    public ICronogramaDAO getCronogramaDAO() {
        return new CronogramaDAO(em);
    }

    public IDatosFacturaDAO getDatosFacturaDAO() {
        return new DatosFacturaDAO(em);
    }

    public IDecisionTrabajoDAO getDecisionTrabajoDAO() {
        return new DecisionTrabajoDAO(em);
    }

    public IDisponibilidadEvaluadorEjeDAO getDisponibilidadEvaluadorEjeDAO() {
        return new DisponibilidadEvaluadorEjeDAO(em);
    }

    public IEjeTematicoDAO getEjeTematicoDAO() {
        return new EjeTematicoDAO(em);
    }

    public IEtapaCongresoDAO getEtapaCongresoDAO() {
        return new EtapaCongresoDAO(em);
    }

    public IEvaluacionDAO getEvaluacionDAO() {
        return new EvaluacionDAO(em);
    }

    public IInscripcionDAO getInscripcionDAO() {
        return new InscripcionDAO(em);
    }

    public IItemAgendaDAO getItemAgendaDAO() {
        return new ItemAgendaDAO(em);
    }

    public INoticiaDAO getNoticiaDAO() {
        return new NoticiaDAO(em);
    }

    public INotificacionDAO getNotificacionDAO() {
        return new NotificacionDAO(em);
    }

    public IPerfilEvaluadorDAO getPerfilEvaluadorDAO() {
        return new PerfilEvaluadorDAO(em);
    }

    public IPlantillaEmailDAO getPlantillaEmailDAO() {
        return new PlantillaEmailDAO(em);
    }

    public IQRDAO getQRDAO() {
        return new QRDAO(em);
    }

    public IRolDAO getRolDAO() {
        return new RolDAO(em);
    }

    public ITrabajoDAO getTrabajoDAO() {
        return new TrabajoDAO(em);
    }

    public IUsuarioDAO getUsuarioDAO() {
        return new UsuarioDAO(em);
    }
}