package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import model.AsignacionEvaluacion;
import model.Evaluacion;
import model.Rol;
import model.Trabajo;
import model.Usuario;
import model.enums.EstadoTrabajo;
import model.enums.TipoRol;
import model.interfaceDao.IAsignacionEvaluacionDAO;
import model.interfaceDao.IEvaluacionDAO;
import model.interfaceDao.IRolDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;

@RequestScoped
public class TrabajoService {

    @Inject
    private ITrabajoDAO trabajoDAO;

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private IRolDAO rolDAO;

    @Inject
    private IAsignacionEvaluacionDAO asignacionDAO;
    
    private IEvaluacionDAO evaluacionDAO;

    public List<Trabajo> listar() {
        return trabajoDAO.listarTodos();
    }

    public Trabajo obtener(Long id) {
        Trabajo trabajo = trabajoDAO.getByID(id);
        if (trabajo == null) {
            throw new NotFoundException("Trabajo no encontrado con id " + id);
        }
        return trabajo;
    }
    public List<Evaluacion> evaluacionesTrabajo(Long id){
    	return evaluacionDAO.getEvaluacionesPorTrabajo(id);
    }

    public Trabajo crear(Trabajo trabajo) {
        if (trabajo == null) {
            throw new BadRequestException("Debe enviar un trabajo");
        }
        trabajoDAO.guardar(trabajo);
        if (trabajo.getAutorContacto() != null && trabajo.getAutorContacto().getId() != null) {
            agregarRolAutor(trabajo.getAutorContacto().getId());
        }
        return trabajo;
    }

    public Trabajo modificar(Long id, Trabajo trabajo) {
        obtener(id);
        trabajo.setId(id);
        return trabajoDAO.modificar(trabajo);
    }

    public void eliminar(Long id) {
        if (!trabajoDAO.deleteByID(id)) {
            throw new NotFoundException("Trabajo no encontrado con id " + id);
        }
    }

    public Trabajo enviar(Long id) {
        Trabajo trabajo = obtener(id);
        trabajo.setEstado(EstadoTrabajo.ENVIADO);
        trabajo.setFechaEnvio(LocalDate.now());
        if (trabajo.getAutorContacto() != null && trabajo.getAutorContacto().getId() != null) {
            agregarRolAutor(trabajo.getAutorContacto().getId());
        }
        return trabajoDAO.modificar(trabajo);
    }

    public AsignacionEvaluacion asignarEvaluador(Long trabajoId, Long evaluadorId, Long asignadoPorId) {
        Trabajo trabajo = obtener(trabajoId);
        Usuario evaluador = obtenerUsuario(evaluadorId);
        Usuario asignadoPor = obtenerUsuario(asignadoPorId);
        AsignacionEvaluacion asignacion = new AsignacionEvaluacion(trabajo, evaluador, asignadoPor);
        asignacionDAO.guardar(asignacion);
        trabajo.setEstado(EstadoTrabajo.EN_EVALUACION);
        trabajoDAO.modificar(trabajo);
        return asignacion;
    }

    public void desasignarEvaluador(Long asignacionId) {
        if (!asignacionDAO.deleteByID(asignacionId)) {
            throw new NotFoundException("Asignación no encontrada con id " + asignacionId);
        }
    }

    private Usuario obtenerUsuario(Long id) {
        if (id == null) {
            throw new BadRequestException("Debe indicar el usuario");
        }
        Usuario usuario = usuarioDAO.getByID(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        return usuario;
    }

    private void agregarRolAutor(Long usuarioId) {
        Usuario usuario = obtenerUsuario(usuarioId);
        Rol rolAutor = rolDAO.getByNombre(TipoRol.AUTOR);
        if (rolAutor == null) {
            rolAutor = new Rol(TipoRol.AUTOR);
            rolDAO.guardar(rolAutor);
        }
        usuario.agregarRol(rolAutor);
        usuarioDAO.modificar(usuario);
    }
}
