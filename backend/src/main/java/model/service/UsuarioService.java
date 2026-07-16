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

    @Inject
    private PasswordService passwordService;

    public List<Usuario> listar() { return usuarioDAO.listarTodos(); }

    public Usuario obtener(Long id) {
        Usuario usuario = usuarioDAO.getByIDConRoles(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        return usuario;
    }

    public Usuario crear(Usuario usuario) {
        if (usuario == null) {
            throw new BadRequestException("Debe enviar un usuario");
        }
        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            throw new BadRequestException("La contraseña es obligatoria");
        }
        if (usuarioDAO.getByEmail(usuario.getEmail()) != null) {
            throw new BadRequestException("Ya existe un usuario con ese email");
        }
        if (usuarioDAO.getByNumeroIdentificacionConRoles(usuario.getNumeroIdentificacion()) != null) {
            throw new BadRequestException("Ya existe un usuario con ese DNI");
        }

        usuario.setPassword(passwordService.hashear(usuario.getPassword()));
        usuarioDAO.guardar(usuario);
        return usuario;
    }

    public Usuario modificar(Long id, Usuario usuario) {
        Usuario existente = obtener(id);
        usuario.setId(id);

        if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
            usuario.setPassword(existente.getPassword());
        } else if (!passwordService.estaHasheado(usuario.getPassword())) {
            usuario.setPassword(passwordService.hashear(usuario.getPassword()));
        }

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
        TipoRol tipoRol = parseTipoRol(tipoRolTexto);
        Usuario usuario = obtener(id);
        if (usuario.tieneRol(tipoRol)) return usuario;
        Rol rol = obtenerOCrearRol(tipoRol);
        usuario.agregarRol(rol);
        return usuarioDAO.modificar(usuario);
    }

    public Usuario quitarRol(Long id, String tipoRolTexto) {
        TipoRol tipoRol = parseTipoRol(tipoRolTexto);
        Usuario usuario = obtener(id);
        boolean eliminado = usuario.getRoles().removeIf(rol -> rol.getNombre() == tipoRol);
        return eliminado ? usuarioDAO.modificar(usuario) : usuario;
    }

    public Usuario promoverAEvaluador(Long id) {
        return asignarRol(id, TipoRol.EVALUADOR.name());
    }

    private Rol obtenerOCrearRol(TipoRol tipoRol) {
        Rol rol = rolDAO.getByNombre(tipoRol);
        if (rol == null) {
            rol = new Rol(tipoRol);
            rolDAO.guardar(rol);
        }
        return rol;
    }

    private TipoRol parseTipoRol(String texto) {
        if (texto == null || texto.isBlank()) throw new BadRequestException("Debe indicar un rol");
        try {
            return TipoRol.valueOf(texto.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Rol inválido: " + texto);
        }
    }
}
