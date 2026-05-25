package servlets;

import java.io.IOException;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.FactoryEM;
import model.Rol;
import model.Usuario;
import model.enums.TipoRol;

@WebServlet("/test-jpa")
public class TestJpaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        EntityManager em = null;

        try {
            em = FactoryEM.getEntityManager();

            em.getTransaction().begin();

            Rol rol = new Rol(TipoRol.ADMIN);
            
            Usuario usuario = new Usuario("Facundo", "Almazan", "facu@test.com", "1234");
            
            usuario.agregarRol(rol);
            
            em.persist(rol);
            em.persist(usuario);
            
            em.getTransaction().commit();

            response.getWriter().println("JPA OK. Rol persistido con id: " + rol.getId());

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            e.printStackTrace(response.getWriter());

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
