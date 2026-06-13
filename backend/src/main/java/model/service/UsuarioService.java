package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import model.Rol;
import model.Usuario;
import model.enums.TipoRol;
import model.interfaceDao.IRolDAO;
import model.interfaceDao.IUsuarioDAO;

@RequestScoped
public class UsuarioService {

    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private IRolDAO rolDAO;

    public List<Usuario> listar() {
        return usuarioDAO.listarTodos();
    }

    public Usuario obtener(Long id) {
        Usuario usuario = usuarioDAO.getByID(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        return usuario;
    }

    public Usuario crear(Usuario usuario) {
        if (usuario == null) {
            throw new BadRequestException("Debe enviar un usuario");
        }
        usuarioDAO.guardar(usuario);
        return usuario;
    }

    public Usuario modificar(Long id, Usuario usuario) {
        obtener(id);
        usuario.setId(id);
        return usuarioDAO.modificar(usuario);
    }

    public void eliminar(Long id) {
        if (!usuarioDAO.deleteByID(id)) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
    }

    public Usuario activar(Long id) {
        Usuario usuario = obtener(id);
        usuario.setActivo(true);
        return usuarioDAO.modificar(usuario);
    }

    public Usuario desactivar(Long id) {
        Usuario usuario = obtener(id);
        usuario.setActivo(false);
        return usuarioDAO.modificar(usuario);
    }

    public Usuario asignarRol(Long id, String tipoRolTexto) {
        Usuario usuario = obtener(id);
        Rol rol = obtenerOCrearRol(parseTipoRol(tipoRolTexto));
        usuario.agregarRol(rol);
        return usuarioDAO.modificar(usuario);
    }

    public Usuario quitarRol(Long id, String tipoRolTexto) {
        Usuario usuario = obtener(id);
        Rol rol = rolDAO.getByNombre(parseTipoRol(tipoRolTexto));
        if (rol == null) {
            throw new NotFoundException("Rol no encontrado: " + tipoRolTexto);
        }
        usuario.quitarRol(rol);
        return usuarioDAO.modificar(usuario);
    }

    public Usuario promoverAEvaluador(Long id) {
        Usuario usuario = obtener(id);
        Rol rolEvaluador = obtenerOCrearRol(TipoRol.EVALUADOR);
        usuario.agregarRol(rolEvaluador);
        return usuarioDAO.modificar(usuario);
    }

    private Rol obtenerOCrearRol(TipoRol tipoRol) {
        Rol rol = rolDAO.getByNombre(tipoRol);
        if (rol == null) {
            rol = new Rol(tipoRol);
            rolDAO.guardar(rol);
        }
        return rol;
    }

    private TipoRol parseTipoRol(String tipoRolTexto) {
        try {
            return TipoRol.valueOf(tipoRolTexto.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Rol inválido: " + tipoRolTexto);
        }
    }
}
