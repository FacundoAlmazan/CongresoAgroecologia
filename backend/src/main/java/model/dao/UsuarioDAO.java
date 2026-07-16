package model.dao;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import model.Usuario;
import model.interfaceDao.IUsuarioDAO;
import model.Rol;

@RequestScoped
public class UsuarioDAO extends GenericDAO<Usuario> implements IUsuarioDAO {

    @Inject
    public UsuarioDAO(EntityManager em) {
        super(em, Usuario.class);
    }

    @Override
    public List<Usuario> getUsuarios() { return listarTodos(); }

    @Override
    public void saveUsuario(Usuario entidad) { guardar(entidad); }

    @Override
    public Usuario modifyUsuario(Usuario entidad) { return modificar(entidad); }

    @Override
    public List<Usuario> getUsuariosByRol(Rol r) {
        return em.createQuery("SELECT DISTINCT u FROM Usuario u JOIN u.roles rol WHERE rol = :rol", Usuario.class)
                .setParameter("rol", r)
                .getResultList();
    }

    @Override
    public List<Usuario> getUsuariosByName(String name, String ap) {
        return em.createQuery(
                "SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(:nombre) AND LOWER(u.apellido) LIKE LOWER(:apellido)",
                Usuario.class)
                .setParameter("nombre", "%" + name + "%")
                .setParameter("apellido", "%" + ap + "%")
                .getResultList();
    }

    @Override
    public Usuario getByEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario getByIDConRoles(Long id) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.id = :id",
                    Usuario.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Usuario getByNumeroIdentificacionConRoles(String numeroIdentificacion) {
        try {
            return em.createQuery(
                    "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.roles " +
                    "WHERE u.numeroIdentificacion = :numeroIdentificacion",
                    Usuario.class)
                    .setParameter("numeroIdentificacion", numeroIdentificacion)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
