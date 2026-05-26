package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Actividad;
import model.Agenda;
import model.AsignacionEvaluacion;
import model.AutorTrabajo;
import model.Circular;
import model.Congreso;
import model.Cronograma;
import model.DatosFactura;
import model.DecisionTrabajo;
import model.DisponibilidadEvaluadorEje;
import model.EjeTematico;
import model.EtapaCongreso;
import model.Evaluacion;
import model.dao.FactoryEM;
import model.dao.FactoryDAO;
import model.Inscripcion;
import model.ItemAgenda;
import model.Noticia;
import model.Notificacion;
import model.PerfilEvaluador;
import model.PlantillaEmail;
import model.QR;
import model.Rol;
import model.Trabajo;
import model.Usuario;
import model.enums.EstadoAsignacion;
import model.enums.EstadoPago;
import model.enums.EstadoTrabajo;
import model.enums.ModalidadPresentacion;
import model.enums.Recomendacion;
import model.enums.TipoIdentificacion;
import model.enums.TipoRol;
import model.enums.TipoTrabajo;
import model.interfaceDao.IActividadDAO;
import model.interfaceDao.IAgendaDAO;
import model.interfaceDao.IAsignacionEvaluacionDAO;
import model.interfaceDao.IAutorTrabajoDAO;
import model.interfaceDao.ICircularDAO;
import model.interfaceDao.ICongresoDAO;
import model.interfaceDao.ICronogramaDAO;
import model.interfaceDao.IDatosFacturaDAO;
import model.interfaceDao.IDecisionTrabajoDAO;
import model.interfaceDao.IDisponibilidadEvaluadorEjeDAO;
import model.interfaceDao.IEjeTematicoDAO;
import model.interfaceDao.IEtapaCongresoDAO;
import model.interfaceDao.IEvaluacionDAO;
import model.interfaceDao.IInscripcionDAO;
import model.interfaceDao.IItemAgendaDAO;
import model.interfaceDao.INoticiaDAO;
import model.interfaceDao.INotificacionDAO;
import model.interfaceDao.IPerfilEvaluadorDAO;
import model.interfaceDao.IPlantillaEmailDAO;
import model.interfaceDao.IQRDAO;
import model.interfaceDao.IRolDAO;
import model.interfaceDao.ITrabajoDAO;
import model.interfaceDao.IUsuarioDAO;

@WebServlet("/test-jpa")
public class TestJpaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String sufijo;

    private IActividadDAO actividadDAO;
    private IAgendaDAO agendaDAO;
    private IAsignacionEvaluacionDAO asignacionEvaluacionDAO;
    private IAutorTrabajoDAO autorTrabajoDAO;
    private ICircularDAO circularDAO;
    private ICongresoDAO congresoDAO;
    private ICronogramaDAO cronogramaDAO;
    private IDatosFacturaDAO datosFacturaDAO;
    private IDecisionTrabajoDAO decisionTrabajoDAO;
    private IDisponibilidadEvaluadorEjeDAO disponibilidadEvaluadorEjeDAO;
    private IEjeTematicoDAO ejeTematicoDAO;
    private IEtapaCongresoDAO etapaCongresoDAO;
    private IEvaluacionDAO evaluacionDAO;
    private IInscripcionDAO inscripcionDAO;
    private IItemAgendaDAO itemAgendaDAO;
    private INoticiaDAO noticiaDAO;
    private INotificacionDAO notificacionDAO;
    private IPerfilEvaluadorDAO perfilEvaluadorDAO;
    private IPlantillaEmailDAO plantillaEmailDAO;
    private IQRDAO qrDAO;
    private IRolDAO rolDAO;
    private ITrabajoDAO trabajoDAO;
    private IUsuarioDAO usuarioDAO;

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

            out.println("=== TEST DAO / JPA / HIBERNATE - CAPA DE PERSISTENCIA ===");
            out.println("Sufijo de datos de prueba: " + sufijo);

            testRol(out);
            testUsuario(out);
            testUsuarioConRol(out);
            testEjeTematico(out);
            testTrabajo(out);
            testAutorTrabajo(out);
            testPerfilEvaluador(out);
            testDisponibilidadEvaluadorEje(out);
            testInscripcion(out);
            testDatosFactura(out);
            testQR(out);
            testAsignacionEvaluacion(out);
            testEvaluacion(out);
            testDecisionTrabajo(out);
            testCronograma(out);
            testActividad(out);
            testAgenda(out);
            testItemAgenda(out);
            testCongreso(out);
            testEtapaCongreso(out);
            testCircular(out);
            testNoticia(out);
            testPlantillaEmail(out);
            testNotificacion(out);

            out.println();
            out.println("=== TODOS LOS TEST DAO FINALIZARON CORRECTAMENTE ===");

        } catch (Exception e) {
            out.println();
            out.println("ERROR EN TEST DAO/JPA:");
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(out);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void inicializarDAOs(EntityManager em) {
        FactoryDAO factoryDAO = new FactoryDAO(em);

        actividadDAO = factoryDAO.getActividadDAO();
        agendaDAO = factoryDAO.getAgendaDAO();
        asignacionEvaluacionDAO = factoryDAO.getAsignacionEvaluacionDAO();
        autorTrabajoDAO = factoryDAO.getAutorTrabajoDAO();
        circularDAO = factoryDAO.getCircularDAO();
        congresoDAO = factoryDAO.getCongresoDAO();
        cronogramaDAO = factoryDAO.getCronogramaDAO();
        datosFacturaDAO = factoryDAO.getDatosFacturaDAO();
        decisionTrabajoDAO = factoryDAO.getDecisionTrabajoDAO();
        disponibilidadEvaluadorEjeDAO = factoryDAO.getDisponibilidadEvaluadorEjeDAO();
        ejeTematicoDAO = factoryDAO.getEjeTematicoDAO();
        etapaCongresoDAO = factoryDAO.getEtapaCongresoDAO();
        evaluacionDAO = factoryDAO.getEvaluacionDAO();
        inscripcionDAO = factoryDAO.getInscripcionDAO();
        itemAgendaDAO = factoryDAO.getItemAgendaDAO();
        noticiaDAO = factoryDAO.getNoticiaDAO();
        notificacionDAO = factoryDAO.getNotificacionDAO();
        perfilEvaluadorDAO = factoryDAO.getPerfilEvaluadorDAO();
        plantillaEmailDAO = factoryDAO.getPlantillaEmailDAO();
        qrDAO = factoryDAO.getQRDAO();
        rolDAO = factoryDAO.getRolDAO();
        trabajoDAO = factoryDAO.getTrabajoDAO();
        usuarioDAO = factoryDAO.getUsuarioDAO();
    }

    private void testRol(PrintWriter out) {
        out.println("\n[TEST DAO] Rol");

        Rol rol = new Rol(TipoRol.OC);
        rolDAO.guardar(rol);
        Long id = id(rol);
        out.println("Alta OK. Id: " + id);

        Rol recuperado = rolDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        recuperado.setNombre(TipoRol.OC);
        rolDAO.modificar(recuperado);
        out.println("Modificacion OK. Nombre: " + rolDAO.getByID(id).getNombre());

        rolDAO.deleteByID(id);
        out.println(rolDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testUsuario(PrintWriter out) {
        out.println("\n[TEST DAO] Usuario");

        Usuario usuario = nuevoUsuario("usuario");
        usuarioDAO.guardar(usuario);
        Long id = id(usuario);
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
        out.println("\n[TEST DAO] Usuario con coleccion de roles");

        Rol rol = obtenerOCrearRol(TipoRol.PARTICIPANTE);
        Usuario usuario = nuevoUsuario("usuario_rol");
        usuario.agregarRol(rol);

        usuarioDAO.guardar(usuario);
        Long id = id(usuario);
        out.println("Alta OK. Id: " + id);

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
        out.println("\n[TEST DAO] EjeTematico");

        EjeTematico eje = nuevoEje("Eje ABM");
        ejeTematicoDAO.guardar(eje);
        Long id = id(eje);
        out.println("Alta OK. Id: " + id);

        EjeTematico recuperado = ejeTematicoDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        recuperado.setDescripcion("Descripcion modificada");
        ejeTematicoDAO.modificar(recuperado);
        out.println("Modificacion OK. Descripcion: " + ejeTematicoDAO.getByID(id).getDescripcion());

        ejeTematicoDAO.deleteByID(id);
        out.println(ejeTematicoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testTrabajo(PrintWriter out) {
        out.println("\n[TEST DAO] Trabajo");

        Usuario autor = crearUsuarioPersistido("autor_trabajo");
        EjeTematico eje = crearEjePersistido("Eje Trabajo");
        Trabajo trabajo = nuevoTrabajo("Trabajo ABM");
        trabajo.setAutorContacto(autor);
        trabajo.agregarEjeTematico(eje);

        trabajoDAO.guardar(trabajo);
        Long id = id(trabajo);
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

    private void testAutorTrabajo(PrintWriter out) {
        out.println("\n[TEST DAO] AutorTrabajo");

        Trabajo trabajo = crearTrabajoPersistido("Trabajo Autor");
        AutorTrabajo autor = new AutorTrabajo("Ana", "Autora", "UNLP", "Argentina", email("autor.trabajo"), 1);
        autor.setTrabajo(trabajo);

        autorTrabajoDAO.guardar(autor);
        Long id = id(autor);
        out.println("Alta OK. Id: " + id);

        AutorTrabajo recuperado = autorTrabajoDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        recuperado.setOrganizacion("UNLP modificada");
        autorTrabajoDAO.modificar(recuperado);
        out.println("Modificacion OK. Organizacion: " + autorTrabajoDAO.getByID(id).getOrganizacion());

        autorTrabajoDAO.deleteByID(id);
        out.println(autorTrabajoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testPerfilEvaluador(PrintWriter out) {
        out.println("\n[TEST DAO] PerfilEvaluador");

        Usuario evaluador = crearUsuarioPersistido("perfil_eval");
        PerfilEvaluador perfil = new PerfilEvaluador(evaluador);
        perfil.setEvaluadorEdicionesPasadas(true);
        perfil.setEvaluadorOtrosCongresos(false);
        perfil.setFormacionAcademica("Maestria, Doctorado");
        perfil.setAreasConocimiento("Ganaderia, Apicultura");
        perfil.setSubareasConocimiento("Sanidad, Nutricion");
        perfil.setComentario("Comentario inicial");

        perfilEvaluadorDAO.guardar(perfil);
        Long id = id(perfil);
        out.println("Alta OK. Id: " + id);

        PerfilEvaluador recuperado = perfilEvaluadorDAO.getByID(id);
        out.println("Recuperacion OK. Formacion: " + recuperado.getFormacionAcademica());

        recuperado.setComentario("Comentario modificado");
        perfilEvaluadorDAO.modificar(recuperado);
        out.println("Modificacion OK. Comentario: " + perfilEvaluadorDAO.getByID(id).getComentario());

        perfilEvaluadorDAO.deleteByID(id);
        out.println(perfilEvaluadorDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDisponibilidadEvaluadorEje(PrintWriter out) {
        out.println("\n[TEST DAO] DisponibilidadEvaluadorEje");

        PerfilEvaluador perfil = crearPerfilEvaluadorPersistido("disp_eval");
        EjeTematico eje = crearEjePersistido("Eje Disponibilidad");
        DisponibilidadEvaluadorEje disponibilidad = new DisponibilidadEvaluadorEje(perfil, eje, 3);

        disponibilidadEvaluadorEjeDAO.guardar(disponibilidad);
        Long id = id(disponibilidad);
        out.println("Alta OK. Id: " + id);

        DisponibilidadEvaluadorEje recuperada = disponibilidadEvaluadorEjeDAO.getByID(id);
        out.println("Recuperacion OK. Cantidad: " + recuperada.getCantidadTrabajos());

        recuperada.setCantidadTrabajos(5);
        disponibilidadEvaluadorEjeDAO.modificar(recuperada);
        out.println("Modificacion OK. Cantidad: " + disponibilidadEvaluadorEjeDAO.getByID(id).getCantidadTrabajos());

        disponibilidadEvaluadorEjeDAO.deleteByID(id);
        out.println(disponibilidadEvaluadorEjeDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testInscripcion(PrintWriter out) {
        out.println("\n[TEST DAO] Inscripcion");

        Usuario usuario = crearUsuarioPersistido("inscripto");
        Inscripcion inscripcion = new Inscripcion(usuario, 12000.0, "comprobante.pdf");
        inscripcion.setTipoInscripcion("Estudiante de grado");
        inscripcion.setFiliacionInstitucional("UNLP");
        inscripcion.setFormasParticipacion("Asistente, Presentacion de trabajo");
        inscripcion.setFechaPago(LocalDate.now());

        inscripcionDAO.guardar(inscripcion);
        Long id = id(inscripcion);
        out.println("Alta OK. Id: " + id);

        Inscripcion recuperada = inscripcionDAO.getByID(id);
        out.println("Recuperacion OK. Estado: " + recuperada.getEstado());

        recuperada.setEstado(EstadoPago.APROBADO);
        inscripcionDAO.modificar(recuperada);
        out.println("Modificacion OK. Estado: " + inscripcionDAO.getByID(id).getEstado());

        inscripcionDAO.deleteByID(id);
        out.println(inscripcionDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDatosFactura(PrintWriter out) {
        out.println("\n[TEST DAO] DatosFactura");

        Inscripcion inscripcion = crearInscripcionPersistida("factura");
        DatosFactura datos = new DatosFactura("Facundo SRL", "20123456789", "Responsable Inscripto", "Calle 1 123", email("factura"));
        datos.setInscripcion(inscripcion);

        datosFacturaDAO.guardar(datos);
        Long id = id(datos);
        out.println("Alta OK. Id: " + id);

        DatosFactura recuperado = datosFacturaDAO.getByID(id);
        out.println("Recuperacion OK. CUIT: " + recuperado.getCuit());

        recuperado.setOtrosDatos("Otros datos modificados");
        datosFacturaDAO.modificar(recuperado);
        out.println("Modificacion OK. Otros datos: " + datosFacturaDAO.getByID(id).getOtrosDatos());

        datosFacturaDAO.deleteByID(id);
        out.println(datosFacturaDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testQR(PrintWriter out) {
        out.println("\n[TEST DAO] QR");

        Inscripcion inscripcion = crearInscripcionPersistida("qr");
        QR qr = new QR("QR-" + sufijo, inscripcion);

        qrDAO.guardar(qr);
        Long id = id(qr);
        out.println("Alta OK. Id: " + id);

        QR recuperado = qrDAO.getByID(id);
        out.println("Recuperacion OK. Codigo: " + recuperado.getCodigo());

        recuperado.setUtilizado(true);
        qrDAO.modificar(recuperado);
        out.println("Modificacion OK. Utilizado: " + qrDAO.getByID(id).getUtilizado());

        qrDAO.deleteByID(id);
        out.println(qrDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testAsignacionEvaluacion(PrintWriter out) {
        out.println("\n[TEST DAO] AsignacionEvaluacion");

        Trabajo trabajo = crearTrabajoPersistido("Trabajo Asignacion");
        Usuario evaluador = crearUsuarioPersistido("evaluador_asignacion");
        Usuario asignador = crearUsuarioPersistido("asignador_asignacion");
        AsignacionEvaluacion asignacion = new AsignacionEvaluacion(trabajo, evaluador, asignador);

        asignacionEvaluacionDAO.guardar(asignacion);
        Long id = id(asignacion);
        out.println("Alta OK. Id: " + id);

        AsignacionEvaluacion recuperada = asignacionEvaluacionDAO.getByID(id);
        out.println("Recuperacion OK. Estado: " + recuperada.getEstado());

        recuperada.setEstado(EstadoAsignacion.ACEPTADA);
        asignacionEvaluacionDAO.modificar(recuperada);
        out.println("Modificacion OK. Estado: " + asignacionEvaluacionDAO.getByID(id).getEstado());

        asignacionEvaluacionDAO.deleteByID(id);
        out.println(asignacionEvaluacionDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEvaluacion(PrintWriter out) {
        out.println("\n[TEST DAO] Evaluacion");

        AsignacionEvaluacion asignacion = crearAsignacionPersistida("evaluacion");
        Evaluacion evaluacion = new Evaluacion(asignacion, Recomendacion.APROBADO_CON_CORRECCION, "Comentarios para autores");
        evaluacion.setPertinenteCongreso(true);
        evaluacion.setEjeTematicoCorrecto(true);
        evaluacion.setTipoTrabajoEvaluado(TipoTrabajo.TRABAJO_CIENTIFICO);
        evaluacion.setModalidadRecomendada(ModalidadPresentacion.POSTER);
        evaluacion.setComentariosComision("Comentario privado");

        evaluacionDAO.guardar(evaluacion);
        Long id = id(evaluacion);
        out.println("Alta OK. Id: " + id);

        Evaluacion recuperada = evaluacionDAO.getByID(id);
        out.println("Recuperacion OK. Recomendacion: " + recuperada.getRecomendacion());

        recuperada.setComentariosAutores("Comentarios modificados");
        evaluacionDAO.modificar(recuperada);
        out.println("Modificacion OK. Comentarios: " + evaluacionDAO.getByID(id).getComentariosAutores());

        evaluacionDAO.deleteByID(id);
        out.println(evaluacionDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDecisionTrabajo(PrintWriter out) {
        out.println("\n[TEST DAO] DecisionTrabajo");

        Trabajo trabajo = crearTrabajoPersistido("Trabajo Decision");
        Usuario organizador = crearUsuarioPersistido("organizador_decision");
        DecisionTrabajo decision = new DecisionTrabajo(trabajo, organizador, EstadoTrabajo.ACEPTADO, "Decision inicial");

        decisionTrabajoDAO.guardar(decision);
        Long id = id(decision);
        out.println("Alta OK. Id: " + id);

        DecisionTrabajo recuperada = decisionTrabajoDAO.getByID(id);
        out.println("Recuperacion OK. Decision: " + recuperada.getDecision());

        recuperada.setComentario("Decision modificada");
        decisionTrabajoDAO.modificar(recuperada);
        out.println("Modificacion OK. Comentario: " + decisionTrabajoDAO.getByID(id).getComentario());

        decisionTrabajoDAO.deleteByID(id);
        out.println(decisionTrabajoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCronograma(PrintWriter out) {
        out.println("\n[TEST DAO] Cronograma");

        Cronograma cronograma = new Cronograma();
        cronogramaDAO.guardar(cronograma);
        Long id = id(cronograma);
        out.println("Alta OK. Id: " + id);

        Cronograma recuperado = cronogramaDAO.getByIDConActividades(id);
        out.println("Recuperacion OK. Actividades: " + recuperado.getActividades().size());

        cronogramaDAO.modificar(recuperado);
        out.println("Modificacion OK.");

        cronogramaDAO.deleteByID(id);
        out.println(cronogramaDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testActividad(PrintWriter out) {
        out.println("\n[TEST DAO] Actividad");

        Cronograma cronograma = crearCronogramaPersistido();
        Actividad actividad = nuevaActividad("Actividad ABM");
        actividad.setCronograma(cronograma);

        actividadDAO.guardar(actividad);
        Long id = id(actividad);
        out.println("Alta OK. Id: " + id);

        Actividad recuperada = actividadDAO.getByID(id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        recuperada.setAula("Aula modificada");
        actividadDAO.modificar(recuperada);
        out.println("Modificacion OK. Aula: " + actividadDAO.getByID(id).getAula());

        actividadDAO.deleteByID(id);
        out.println(actividadDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testAgenda(PrintWriter out) {
        out.println("\n[TEST DAO] Agenda");

        Usuario usuario = crearUsuarioPersistido("agenda_usuario");
        Agenda agenda = new Agenda(usuario);

        agendaDAO.guardar(agenda);
        Long id = id(agenda);
        out.println("Alta OK. Id: " + id);

        Agenda recuperada = agendaDAO.getByIDConItems(id);
        out.println("Recuperacion OK. Usuario: " + recuperada.getUsuario().getEmail());

        agendaDAO.modificar(recuperada);
        out.println("Modificacion OK.");

        agendaDAO.deleteByID(id);
        out.println(agendaDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testItemAgenda(PrintWriter out) {
        out.println("\n[TEST DAO] ItemAgenda");

        Usuario usuario = crearUsuarioPersistido("item_agenda_usuario");
        Agenda agenda = crearAgendaPersistida(usuario);
        Actividad actividad = crearActividadPersistida("Actividad ItemAgenda");
        ItemAgenda item = new ItemAgenda(agenda, actividad);

        itemAgendaDAO.guardar(item);
        Long id = id(item);
        out.println("Alta OK. Id: " + id);

        ItemAgenda recuperado = itemAgendaDAO.getByID(id);
        out.println("Recuperacion OK. Actividad: " + recuperado.getActividad().getTitulo());

        recuperado.setActividad(actividad);
        itemAgendaDAO.modificar(recuperado);
        out.println("Modificacion OK.");

        itemAgendaDAO.deleteByID(id);
        out.println(itemAgendaDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCongreso(PrintWriter out) {
        out.println("\n[TEST DAO] Congreso");

        Cronograma cronograma = crearCronogramaPersistido();
        Congreso congreso = new Congreso("Congreso ABM", "V");
        congreso.setFechaInicio(LocalDate.now());
        congreso.setFechaFin(LocalDate.now().plusDays(3));
        congreso.setCronograma(cronograma);

        congresoDAO.guardar(congreso);
        Long id = id(congreso);
        out.println("Alta OK. Id: " + id);

        Congreso recuperado = congresoDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        recuperado.setNombre("Congreso modificado");
        congresoDAO.modificar(recuperado);
        out.println("Modificacion OK. Nombre: " + congresoDAO.getByID(id).getNombre());

        congresoDAO.deleteByID(id);
        out.println(congresoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEtapaCongreso(PrintWriter out) {
        out.println("\n[TEST DAO] EtapaCongreso");

        Congreso congreso = crearCongresoPersistido("Congreso Etapa");
        EtapaCongreso etapa = new EtapaCongreso("Recepcion de trabajos", LocalDate.now(), LocalDate.now().plusDays(30));
        etapa.setCongreso(congreso);

        etapaCongresoDAO.guardar(etapa);
        Long id = id(etapa);
        out.println("Alta OK. Id: " + id);

        EtapaCongreso recuperada = etapaCongresoDAO.getByID(id);
        out.println("Recuperacion OK. Nombre: " + recuperada.getNombre());

        recuperada.setNombre("Etapa modificada");
        etapaCongresoDAO.modificar(recuperada);
        out.println("Modificacion OK. Nombre: " + etapaCongresoDAO.getByID(id).getNombre());

        etapaCongresoDAO.deleteByID(id);
        out.println(etapaCongresoDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCircular(PrintWriter out) {
        out.println("\n[TEST DAO] Circular");

        Usuario usuario = crearUsuarioPersistido("circular_publicador");
        Circular circular = new Circular("Circular ABM", "circular.pdf", usuario);
        circular.setFechaPublicacion(LocalDate.now());

        circularDAO.guardar(circular);
        Long id = id(circular);
        out.println("Alta OK. Id: " + id);

        Circular recuperada = circularDAO.getByID(id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        recuperada.setTitulo("Circular modificada");
        circularDAO.modificar(recuperada);
        out.println("Modificacion OK. Titulo: " + circularDAO.getByID(id).getTitulo());

        circularDAO.deleteByID(id);
        out.println(circularDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testNoticia(PrintWriter out) {
        out.println("\n[TEST DAO] Noticia");

        Usuario usuario = crearUsuarioPersistido("noticia_publicador");
        Noticia noticia = new Noticia("Noticia ABM", "Contenido de prueba", usuario);
        noticia.setFechaPublicacion(LocalDate.now());

        noticiaDAO.guardar(noticia);
        Long id = id(noticia);
        out.println("Alta OK. Id: " + id);

        Noticia recuperada = noticiaDAO.getByID(id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        recuperada.setContenido("Contenido modificado");
        noticiaDAO.modificar(recuperada);
        out.println("Modificacion OK. Contenido: " + noticiaDAO.getByID(id).getContenido());

        noticiaDAO.deleteByID(id);
        out.println(noticiaDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testPlantillaEmail(PrintWriter out) {
        out.println("\n[TEST DAO] PlantillaEmail");

        PlantillaEmail plantilla = new PlantillaEmail("Asunto ABM", "Cuerpo de prueba");
        plantillaEmailDAO.guardar(plantilla);
        Long id = id(plantilla);
        out.println("Alta OK. Id: " + id);

        PlantillaEmail recuperada = plantillaEmailDAO.getByID(id);
        out.println("Recuperacion OK. Asunto: " + recuperada.getAsunto());

        recuperada.setCuerpo("Cuerpo modificado");
        plantillaEmailDAO.modificar(recuperada);
        out.println("Modificacion OK. Cuerpo: " + plantillaEmailDAO.getByID(id).getCuerpo());

        plantillaEmailDAO.deleteByID(id);
        out.println(plantillaEmailDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testNotificacion(PrintWriter out) {
        out.println("\n[TEST DAO] Notificacion");

        Usuario destinatario = crearUsuarioPersistido("notificacion_destinatario");
        PlantillaEmail plantilla = crearPlantillaPersistida();
        Notificacion notificacion = new Notificacion("Notificacion ABM", "Mensaje de prueba", destinatario);
        notificacion.setPlantilla(plantilla);
        notificacion.setFechaEnvio(LocalDate.now());

        notificacionDAO.guardar(notificacion);
        Long id = id(notificacion);
        out.println("Alta OK. Id: " + id);

        Notificacion recuperada = notificacionDAO.getByID(id);
        out.println("Recuperacion OK. Asunto: " + recuperada.getAsunto());

        recuperada.setMensaje("Mensaje modificado");
        notificacionDAO.modificar(recuperada);
        out.println("Modificacion OK. Mensaje: " + notificacionDAO.getByID(id).getMensaje());

        notificacionDAO.deleteByID(id);
        out.println(notificacionDAO.getByID(id) == null ? "Baja OK." : "Baja FALLIDA.");
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
        Trabajo trabajo = new Trabajo(titulo + " " + sufijo, "Resumen de prueba", TipoTrabajo.TRABAJO_CIENTIFICO, "clave" + sufijo);
        trabajo.setArchivo("archivo.docx");
        trabajo.setFechaEnvio(LocalDate.now());
        trabajo.setTelefonoContacto("+5492211234567");
        trabajo.setModalidadSolicitada(ModalidadPresentacion.ORAL);
        trabajo.setEstado(EstadoTrabajo.BORRADOR);
        return trabajo;
    }

    private Trabajo crearTrabajoPersistido(String titulo) {
        Usuario autor = crearUsuarioPersistido("autor_" + titulo.replace(" ", "_"));
        Trabajo trabajo = nuevoTrabajo(titulo);
        trabajo.setAutorContacto(autor);
        trabajoDAO.guardar(trabajo);
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

    private PerfilEvaluador crearPerfilEvaluadorPersistido(String prefijo) {
        Usuario usuario = crearUsuarioPersistido(prefijo);
        PerfilEvaluador perfil = new PerfilEvaluador(usuario);
        perfil.setFormacionAcademica("Curso de posgrado");
        perfil.setAreasConocimiento("Agricultura urbana");
        perfil.setSubareasConocimiento("Manejo de suelo");
        perfilEvaluadorDAO.guardar(perfil);
        return perfil;
    }

    private Inscripcion crearInscripcionPersistida(String prefijo) {
        Usuario usuario = crearUsuarioPersistido("usuario_" + prefijo);
        Inscripcion inscripcion = new Inscripcion(usuario, 15000.0, "comprobante_" + prefijo + ".pdf");
        inscripcion.setFechaPago(LocalDate.now());
        inscripcionDAO.guardar(inscripcion);
        return inscripcion;
    }

    private AsignacionEvaluacion crearAsignacionPersistida(String prefijo) {
        Trabajo trabajo = crearTrabajoPersistido("Trabajo " + prefijo);
        Usuario evaluador = crearUsuarioPersistido("evaluador_" + prefijo);
        Usuario asignador = crearUsuarioPersistido("asignador_" + prefijo);
        AsignacionEvaluacion asignacion = new AsignacionEvaluacion(trabajo, evaluador, asignador);
        asignacionEvaluacionDAO.guardar(asignacion);
        return asignacion;
    }

    private Cronograma crearCronogramaPersistido() {
        Cronograma cronograma = new Cronograma();
        cronogramaDAO.guardar(cronograma);
        return cronograma;
    }

    private Actividad nuevaActividad(String titulo) {
        Actividad actividad = new Actividad(titulo + " " + sufijo, "Aula 1", LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        actividad.setDescripcion("Descripcion de actividad");
        return actividad;
    }

    private Actividad crearActividadPersistida(String titulo) {
        Cronograma cronograma = crearCronogramaPersistido();
        Actividad actividad = nuevaActividad(titulo);
        actividad.setCronograma(cronograma);
        actividadDAO.guardar(actividad);
        return actividad;
    }

    private Agenda crearAgendaPersistida(Usuario usuario) {
        Agenda agenda = new Agenda(usuario);
        agendaDAO.guardar(agenda);
        return agenda;
    }

    private Congreso crearCongresoPersistido(String nombre) {
        Cronograma cronograma = crearCronogramaPersistido();
        Congreso congreso = new Congreso(nombre + " " + sufijo, "V");
        congreso.setFechaInicio(LocalDate.now());
        congreso.setFechaFin(LocalDate.now().plusDays(3));
        congreso.setCronograma(cronograma);
        congresoDAO.guardar(congreso);
        return congreso;
    }

    private PlantillaEmail crearPlantillaPersistida() {
        PlantillaEmail plantilla = new PlantillaEmail("Asunto plantilla " + sufijo, "Cuerpo plantilla");
        plantillaEmailDAO.guardar(plantilla);
        return plantilla;
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

    private Long id(Object entidad) {
        try {
            return (Long) entidad.getClass().getMethod("getId").invoke(entidad);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el id de " + entidad.getClass().getSimpleName(), e);
        }
    }

    private String email(String prefijo) {
        return prefijo + "." + sufijo + "@test.com";
    }
}