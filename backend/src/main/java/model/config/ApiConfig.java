package model.config;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.ApplicationPath;

import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.glassfish.jersey.jackson.JacksonFeature;
import jakarta.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.internal.inject.AbstractBinder;

import model.dao.*;
import model.interfaceDao.*;
import model.service.*;

@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {

	
	public ApiConfig() {
	    packages("model.resource");

	    register(JacksonFeature.class);

	    register(new ContextResolver<ObjectMapper>() {
	        private final ObjectMapper mapper = new ObjectMapper()
	                .registerModule(new JavaTimeModule())
	                .configure(com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

	        @Override
	        public ObjectMapper getContext(Class<?> type) {
	            return mapper;
	        }
	    });

	    register(new AbstractBinder() {
	        @Override
	        protected void configure() {
	            bindFactory(new Supplier<EntityManager>() {
	                @Override
	                public EntityManager get() {
	                    return FactoryEM.getEntityManager();
	                }
	            }).to(EntityManager.class);

                bind(ActividadDAO.class).to(IActividadDAO.class);
                bind(AgendaDAO.class).to(IAgendaDAO.class);
                bind(AsignacionEvaluacionDAO.class).to(IAsignacionEvaluacionDAO.class);
                bind(AutorTrabajoDAO.class).to(IAutorTrabajoDAO.class);
                bind(CircularDAO.class).to(ICircularDAO.class);
                bind(CongresoDAO.class).to(ICongresoDAO.class);
                bind(CronogramaDAO.class).to(ICronogramaDAO.class);
                bind(DatosFacturaDAO.class).to(IDatosFacturaDAO.class);
                bind(DecisionTrabajoDAO.class).to(IDecisionTrabajoDAO.class);
                bind(DisponibilidadEvaluadorEjeDAO.class).to(IDisponibilidadEvaluadorEjeDAO.class);
                bind(EjeTematicoDAO.class).to(IEjeTematicoDAO.class);
                bind(EtapaCongresoDAO.class).to(IEtapaCongresoDAO.class);
                bind(EvaluacionDAO.class).to(IEvaluacionDAO.class);
                bind(InscripcionDAO.class).to(IInscripcionDAO.class);
                bind(ItemAgendaDAO.class).to(IItemAgendaDAO.class);
                bind(NoticiaDAO.class).to(INoticiaDAO.class);
                bind(NotificacionDAO.class).to(INotificacionDAO.class);
                bind(PerfilEvaluadorDAO.class).to(IPerfilEvaluadorDAO.class);
                bind(PlantillaEmailDAO.class).to(IPlantillaEmailDAO.class);
                bind(QRDAO.class).to(IQRDAO.class);
                bind(RolDAO.class).to(IRolDAO.class);
                bind(TrabajoDAO.class).to(ITrabajoDAO.class);
                bind(UsuarioDAO.class).to(IUsuarioDAO.class);

                bindAsContract(UsuarioService.class);
                bindAsContract(ActividadService.class);
                bindAsContract(TrabajoService.class);
                bindAsContract(AsignacionEvaluacionService.class);
                bindAsContract(PagoService.class);
	        }
	    });
	}
}