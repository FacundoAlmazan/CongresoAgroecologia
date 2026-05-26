package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.dao.FactoryDAO;
import model.dao.FactoryEM;
import model.DisponibilidadEvaluadorEje;
import model.EjeTematico;
import model.Inscripcion;
import model.PerfilEvaluador;
import model.QR;
import model.Rol;
import model.Trabajo;
import model.Usuario;
import model.enums.EstadoPago;
import model.enums.EstadoTrabajo;
import model.enums.ModalidadPresentacion;
import model.enums.TipoIdentificacion;
import model.enums.TipoRol;
import model.enums.TipoTrabajo;
import model.interfaceDao.IDisponibilidadEvaluadorEjeDAO;
import model.interfaceDao.IEjeTematicoDAO;
import model.interfaceDao.IInscripcionDAO;
import model.interfaceDao.IPerfilEvaluadorDAO;
import model.interfaceDao.IQRDAO;
import model.interfaceDao.IRolDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;

@WebServlet("/test-jpa")
public class TestJpaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String sufijo;

    private IUsuarioDAO usuarioDAO;
    private IRolDAO rolDAO;
    private IEjeTematicoDAO ejeTematicoDAO;
    private ITrabajoDAO trabajoDAO;
    private IPerfilEvaluadorDAO perfilEvaluadorDAO;
    private IDisponibilidadEvaluadorEjeDAO disponibilidadEvaluadorEjeDAO;
    private IInscripcionDAO inscripcionDAO;
    private IQRDAO qrDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManager em = null;
        sufijo = String.valueOf(System.currentTimeMillis());

        try {
            em = FactoryEM.getEntityManager();
            inicializarDAOs(em);

            out.println("=== TEST DAO / JPA / HIBERNATE ===");
            out.println("Prueba simplificada de persistencia");
            out.println("Sufijo: " + sufijo);

            testUsuario(out);
            testUsuarioConRol(out);
            testEjeTematico(out);
            testTrabajoConEje(out);
            testPerfilEvaluadorConDisponibilidad(out);
            testInscripcionConQR(out);

            out.println();
            out.println("=== TEST FINALIZADO CORRECTAMENTE ===");

        } catch (Exception e) {
            out.println();
            out.println("ERROR EN TEST DAO/JPA:");
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(out);

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private void inicializarDAOs(EntityManager em) {
        FactoryDAO factoryDAO = new FactoryDAO(em);

        usuarioDAO = factoryDAO.getUsuarioDAO();
        rolDAO = factoryDAO.getRolDAO();
        ejeTematicoDAO = factoryDAO.getEjeTematicoDAO();
        trabajoDAO = factoryDAO.getTrabajoDAO();
        perfilEvaluadorDAO = factoryDAO.getPerfilEvaluadorDAO();
        disponibilidadEvaluadorEjeDAO = factoryDAO.getDisponibilidadEvaluadorEjeDAO();
        inscripcionDAO = factoryDAO.getInscripcionDAO();
        qrDAO = factoryDAO.getQRDAO();
    }

    private void testUsuario(PrintWriter out) {
        out.println();
        out.println("[TEST] Usuario - ABM");

        Usuario usuario = nuevoUsuario("usuario_abm");
        usuarioDAO.guardar(usuario);
        Long id = usuario.getId();
        out.println("Alta OK. Id: " + id);

        Usuario recuperado = usuarioDAO.getByID(id);
        out.println("Recuperacion OK. Email: " + recuperado.getEmail());

        recuperado.setTelefono("+549221000000");
        usuarioDAO.modificar(recuperado);
        out.println("Modificacion OK. Telefono: " + usuarioDAO.getByID(id).getTelefono());

        usuarioDAO.deleteByID(id);
        out.println(usuarioDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testUsuarioConRol(PrintWriter out) {
        out.println();
        out.println("[TEST] Usuario con Rol - relacion ManyToMany");

        Rol rol = obtenerOCrearRol(TipoRol.PARTICIPANTE);

        Usuario usuario = nuevoUsuario("usuario_rol");
        usuario.agregarRol(rol);
        usuarioDAO.guardar(usuario);
        Long id = usuario.getId();
        out.println("Alta OK. Usuario id: " + id);

        Usuario recuperado = usuarioDAO.getByIDConRoles(id);
        out.println("Recuperacion OK. Cantidad roles: " + recuperado.getRoles().size());

        recuperado.setNombre("Usuario Modificado");
        usuarioDAO.modificar(recuperado);
        out.println("Modificacion OK. Nombre: " + usuarioDAO.getByID(id).getNombre());

        recuperado = usuarioDAO.getByIDConRoles(id);
        recuperado.getRoles().clear();
        usuarioDAO.modificar(recuperado);
        usuarioDAO.deleteByID(id);
        out.println(usuarioDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEjeTematico(PrintWriter out) {
        out.println();
        out.println("[TEST] EjeTematico - ABM");

        EjeTematico eje = nuevoEje("Eje ABM");
        ejeTematicoDAO.guardar(eje);
        Long id = eje.getId();
        out.println("Alta OK. Id: " + id);

        EjeTematico recuperado = ejeTematicoDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        recuperado.setDescripcion("Descripcion modificada");
        ejeTematicoDAO.modificar(recuperado);
        out.println("Modificacion OK. Descripcion: " + ejeTematicoDAO.getByID(id).getDescripcion());

        ejeTematicoDAO.deleteByID(id);
        out.println(ejeTematicoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testTrabajoConEje(PrintWriter out) {
        out.println();
        out.println("[TEST] Trabajo con EjeTematico - ABM y coleccion");

        Usuario autor = crearUsuarioPersistido("autor_trabajo");
        EjeTematico eje = crearEjePersistido("Eje Trabajo");

        Trabajo trabajo = nuevoTrabajo("Trabajo ABM");
        trabajo.setAutorContacto(autor);
        trabajo.agregarEjeTematico(eje);

        trabajoDAO.guardar(trabajo);
        Long id = trabajo.getId();
        out.println("Alta OK. Id: " + id);

        Trabajo recuperado = trabajoDAO.getByIDConAutoresYEjes(id);
        out.println("Recuperacion OK. Titulo: " + recuperado.getTitulo());
        out.println("Ejes cargados: " + recuperado.getEjesTematicos().size());

        recuperado.setEstado(EstadoTrabajo.ENVIADO);
        trabajoDAO.modificar(recuperado);
        out.println("Modificacion OK. Estado: " + trabajoDAO.getByID(id).getEstado());

        recuperado = trabajoDAO.getByIDConAutoresYEjes(id);
        recuperado.getEjesTematicos().clear();
        trabajoDAO.modificar(recuperado);
        trabajoDAO.deleteByID(id);
        out.println(trabajoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testPerfilEvaluadorConDisponibilidad(PrintWriter out) {
        out.println();
        out.println("[TEST] PerfilEvaluador con DisponibilidadEvaluadorEje");

        Usuario evaluador = crearUsuarioPersistido("evaluador");
        PerfilEvaluador perfil = new PerfilEvaluador(evaluador);
        perfil.setEvaluadorEdicionesPasadas(true);
        perfil.setEvaluadorOtrosCongresos(false);
        perfil.setFormacionAcademica("Curso de posgrado");
        perfil.setAreasConocimiento("Agricultura urbana");
        perfil.setSubareasConocimiento("Manejo de suelo");
        perfil.setComentario("Comentario inicial");
        perfilEvaluadorDAO.guardar(perfil);
        out.println("Alta Perfil OK. Id: " + perfil.getId());

        EjeTematico eje = crearEjePersistido("Eje Evaluador");
        DisponibilidadEvaluadorEje disponibilidad = new DisponibilidadEvaluadorEje(perfil, eje, 3);
        disponibilidadEvaluadorEjeDAO.guardar(disponibilidad);
        Long id = disponibilidad.getId();
        out.println("Alta Disponibilidad OK. Id: " + id);

        DisponibilidadEvaluadorEje recuperada = disponibilidadEvaluadorEjeDAO.getByID(id);
        out.println("Recuperacion OK. Cantidad: " + recuperada.getCantidadTrabajos());

        recuperada.setCantidadTrabajos(5);
        disponibilidadEvaluadorEjeDAO.modificar(recuperada);
        out.println("Modificacion OK. Cantidad: " + disponibilidadEvaluadorEjeDAO.getByID(id).getCantidadTrabajos());

        disponibilidadEvaluadorEjeDAO.deleteByID(id);
        out.println(disponibilidadEvaluadorEjeDAO.getByID(id) == null ? "Baja disponibilidad OK." : "Baja disponibilidad FALLIDA.");
    }

    private void testInscripcionConQR(PrintWriter out) {
        out.println();
        out.println("[TEST] Inscripcion con QR");

        Usuario usuario = crearUsuarioPersistido("inscripto");
        Inscripcion inscripcion = new Inscripcion(usuario, 12000.0, "comprobante_" + sufijo + ".pdf");
        inscripcion.setTipoInscripcion("Estudiante de grado");
        inscripcion.setFiliacionInstitucional("UNLP");
        inscripcion.setFormasParticipacion("Asistente");
        inscripcion.setFechaPago(LocalDate.now());

        inscripcionDAO.guardar(inscripcion);
        Long idInscripcion = inscripcion.getId();
        out.println("Alta Inscripcion OK. Id: " + idInscripcion);

        Inscripcion recuperada = inscripcionDAO.getByID(idInscripcion);
        out.println("Recuperacion OK. Estado: " + recuperada.getEstado());

        recuperada.setEstado(EstadoPago.APROBADO);
        inscripcionDAO.modificar(recuperada);
        out.println("Modificacion OK. Estado: " + inscripcionDAO.getByID(idInscripcion).getEstado());

        QR qr = new QR("QR-" + sufijo, recuperada);
        qrDAO.guardar(qr);
        Long idQr = qr.getId();
        out.println("Alta QR OK. Id: " + idQr);

        QR qrRecuperado = qrDAO.getByID(idQr);
        out.println("Recuperacion QR OK. Codigo: " + qrRecuperado.getCodigo());

        qrRecuperado.setUtilizado(true);
        qrDAO.modificar(qrRecuperado);
        out.println("Modificacion QR OK. Utilizado: " + qrDAO.getByID(idQr).getUtilizado());

        qrDAO.deleteByID(idQr);
        out.println(qrDAO.getByID(idQr) == null ? "Baja QR OK." : "Baja QR FALLIDA.");

        inscripcionDAO.deleteByID(idInscripcion);
        out.println(inscripcionDAO.getByID(idInscripcion) == null ? "Baja Inscripcion OK." : "Baja Inscripcion FALLIDA.");
    }

    private Usuario nuevoUsuario(String prefijo) {
        Usuario usuario = new Usuario("Nombre " + prefijo, "Apellido " + prefijo, email(prefijo), "1234");
        usuario.setTelefono("+5492211234567");
        usuario.setTipoIdentificacion(TipoIdentificacion.DNI);
        usuario.setNumeroIdentificacion("DNI" + sufijo.substring(Math.max(0, sufijo.length() - 8)));
        usuario.setNacionalidad("Argentina");
        usuario.setInstitucion("UNLP");
        return usuario;
    }

    private Usuario crearUsuarioPersistido(String prefijo) {
        Usuario usuario = nuevoUsuario(prefijo);
        usuarioDAO.guardar(usuario);
        return usuario;
    }

    private Trabajo nuevoTrabajo(String titulo) {
        Trabajo trabajo = new Trabajo(
                titulo + " " + sufijo,
                "Resumen de prueba",
                TipoTrabajo.TRABAJO_CIENTIFICO,
                "clave" + sufijo
        );
        trabajo.setArchivo("archivo.docx");
        trabajo.setFechaEnvio(LocalDate.now());
        trabajo.setTelefonoContacto("+5492211234567");
        trabajo.setModalidadSolicitada(ModalidadPresentacion.ORAL);
        trabajo.setEstado(EstadoTrabajo.BORRADOR);
        return trabajo;
    }

    private EjeTematico nuevoEje(String nombre) {
        return new EjeTematico(nombre + " " + sufijo, "Descripcion de prueba");
    }

    private EjeTematico crearEjePersistido(String nombre) {
        EjeTematico eje = nuevoEje(nombre);
        ejeTematicoDAO.guardar(eje);
        return eje;
    }

    private Rol obtenerOCrearRol(TipoRol tipoRol) {
        Rol existente = rolDAO.getByNombre(tipoRol);
        if (existente != null) {
            return existente;
        }

        Rol rol = new Rol(tipoRol);
        rolDAO.guardar(rol);
        return rol;
    }

    private String email(String prefijo) {
        return prefijo + "." + sufijo + "@test.com";
    }
}