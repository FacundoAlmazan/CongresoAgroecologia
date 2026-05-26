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
import model.FactoryEM;
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

@WebServlet("/test-jpa")
public class TestJpaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String sufijo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        EntityManager em = null;
        sufijo = String.valueOf(System.currentTimeMillis());

        try {
            em = FactoryEM.getEntityManager();

            out.println("=== TEST JPA / HIBERNATE - CAPA DE PERSISTENCIA ===");
            out.println("Sufijo de datos de prueba: " + sufijo);

            testRol(em, out);
            testUsuario(em, out);
            testUsuarioConRol(em, out);
            testEjeTematico(em, out);
            testTrabajo(em, out);
            testAutorTrabajo(em, out);
            testPerfilEvaluador(em, out);
            testDisponibilidadEvaluadorEje(em, out);
            testInscripcion(em, out);
            testDatosFactura(em, out);
            testQR(em, out);
            testAsignacionEvaluacion(em, out);
            testEvaluacion(em, out);
            testDecisionTrabajo(em, out);
            testCronograma(em, out);
            testActividad(em, out);
            testAgenda(em, out);
            testItemAgenda(em, out);
            testCongreso(em, out);
            testEtapaCongreso(em, out);
            testCircular(em, out);
            testNoticia(em, out);
            testPlantillaEmail(em, out);
            testNotificacion(em, out);

            out.println();
            out.println("=== TODOS LOS TEST FINALIZARON CORRECTAMENTE ===");

        } catch (Exception e) {
            out.println();
            out.println("ERROR EN TEST JPA:");
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

    private void testRol(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Rol");

        Rol rol = new Rol(TipoRol.OC);
        persistir(em, rol);
        Long id = id(rol);
        out.println("Alta OK. Id: " + id);

        Rol recuperado = em.find(Rol.class, id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        modificar(em, () -> recuperado.setNombre(TipoRol.OC));
        out.println("Modificacion OK. Nombre: " + em.find(Rol.class, id).getNombre());

        eliminar(em, recuperado);
        out.println(em.find(Rol.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testUsuario(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Usuario");

        Usuario usuario = nuevoUsuario("usuario");
        persistir(em, usuario);
        Long id = id(usuario);
        out.println("Alta OK. Id: " + id);

        Usuario recuperado = em.find(Usuario.class, id);
        out.println("Recuperacion OK. Email: " + recuperado.getEmail());

        modificar(em, () -> recuperado.setTelefono("+549221000000"));
        out.println("Modificacion OK. Telefono: " + em.find(Usuario.class, id).getTelefono());

        eliminar(em, recuperado);
        out.println(em.find(Usuario.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testUsuarioConRol(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Usuario con coleccion de roles");

        Rol rol = obtenerOCrearRol(em, TipoRol.PARTICIPANTE);
        Usuario usuario = nuevoUsuario("usuario_rol");
        usuario.agregarRol(rol);

        persistir(em, usuario);
        Long id = id(usuario);
        out.println("Alta OK. Id: " + id);

        Usuario recuperado = em.find(Usuario.class, id);
        out.println("Recuperacion OK. Cantidad roles: " + recuperado.getRoles().size());

        modificar(em, () -> recuperado.setNombre("Usuario Modificado"));
        out.println("Modificacion OK. Nombre: " + em.find(Usuario.class, id).getNombre());

        modificar(em, () -> recuperado.getRoles().clear());
        eliminar(em, recuperado);
        out.println(em.find(Usuario.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEjeTematico(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] EjeTematico");

        EjeTematico eje = nuevoEje("Eje ABM");
        persistir(em, eje);
        Long id = id(eje);
        out.println("Alta OK. Id: " + id);

        EjeTematico recuperado = em.find(EjeTematico.class, id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        modificar(em, () -> recuperado.setDescripcion("Descripcion modificada"));
        out.println("Modificacion OK. Descripcion: " + em.find(EjeTematico.class, id).getDescripcion());

        eliminar(em, recuperado);
        out.println(em.find(EjeTematico.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testTrabajo(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Trabajo");

        Usuario autor = crearUsuarioPersistido(em, "autor_trabajo");
        EjeTematico eje = crearEjePersistido(em, "Eje Trabajo");
        Trabajo trabajo = nuevoTrabajo("Trabajo ABM");
        trabajo.setAutorContacto(autor);
        trabajo.agregarEjeTematico(eje);

        persistir(em, trabajo);
        Long id = id(trabajo);
        out.println("Alta OK. Id: " + id);

        Trabajo recuperado = em.find(Trabajo.class, id);
        out.println("Recuperacion OK. Titulo: " + recuperado.getTitulo());

        modificar(em, () -> recuperado.setEstado(EstadoTrabajo.ENVIADO));
        out.println("Modificacion OK. Estado: " + em.find(Trabajo.class, id).getEstado());

        modificar(em, () -> recuperado.getEjesTematicos().clear());
        eliminar(em, recuperado);
        out.println(em.find(Trabajo.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testAutorTrabajo(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] AutorTrabajo");

        Trabajo trabajo = crearTrabajoPersistido(em, "Trabajo Autor");
        AutorTrabajo autor = new AutorTrabajo("Ana", "Autora", "UNLP", "Argentina", email("autor.trabajo"), 1);
        autor.setTrabajo(trabajo);

        persistir(em, autor);
        Long id = id(autor);
        out.println("Alta OK. Id: " + id);

        AutorTrabajo recuperado = em.find(AutorTrabajo.class, id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        modificar(em, () -> recuperado.setOrganizacion("UNLP modificada"));
        out.println("Modificacion OK. Organizacion: " + em.find(AutorTrabajo.class, id).getOrganizacion());

        eliminar(em, recuperado);
        out.println(em.find(AutorTrabajo.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testPerfilEvaluador(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] PerfilEvaluador");

        Usuario evaluador = crearUsuarioPersistido(em, "perfil_eval");
        PerfilEvaluador perfil = new PerfilEvaluador(evaluador);
        perfil.setEvaluadorEdicionesPasadas(true);
        perfil.setEvaluadorOtrosCongresos(false);
        perfil.setFormacionAcademica("Maestria, Doctorado");
        perfil.setAreasConocimiento("Ganaderia, Apicultura");
        perfil.setSubareasConocimiento("Sanidad, Nutricion");
        perfil.setComentario("Comentario inicial");

        persistir(em, perfil);
        Long id = id(perfil);
        out.println("Alta OK. Id: " + id);

        PerfilEvaluador recuperado = em.find(PerfilEvaluador.class, id);
        out.println("Recuperacion OK. Formacion: " + recuperado.getFormacionAcademica());

        modificar(em, () -> recuperado.setComentario("Comentario modificado"));
        out.println("Modificacion OK. Comentario: " + em.find(PerfilEvaluador.class, id).getComentario());

        eliminar(em, recuperado);
        out.println(em.find(PerfilEvaluador.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDisponibilidadEvaluadorEje(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] DisponibilidadEvaluadorEje");

        PerfilEvaluador perfil = crearPerfilEvaluadorPersistido(em, "disp_eval");
        EjeTematico eje = crearEjePersistido(em, "Eje Disponibilidad");
        DisponibilidadEvaluadorEje disponibilidad = new DisponibilidadEvaluadorEje(perfil, eje, 3);

        persistir(em, disponibilidad);
        Long id = id(disponibilidad);
        out.println("Alta OK. Id: " + id);

        DisponibilidadEvaluadorEje recuperada = em.find(DisponibilidadEvaluadorEje.class, id);
        out.println("Recuperacion OK. Cantidad: " + recuperada.getCantidadTrabajos());

        modificar(em, () -> recuperada.setCantidadTrabajos(5));
        out.println("Modificacion OK. Cantidad: " + em.find(DisponibilidadEvaluadorEje.class, id).getCantidadTrabajos());

        eliminar(em, recuperada);
        out.println(em.find(DisponibilidadEvaluadorEje.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testInscripcion(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Inscripcion");

        Usuario usuario = crearUsuarioPersistido(em, "inscripto");
        Inscripcion inscripcion = new Inscripcion(usuario, 12000.0, "comprobante.pdf");
        inscripcion.setTipoInscripcion("Estudiante de grado");
        inscripcion.setFiliacionInstitucional("UNLP");
        inscripcion.setFormasParticipacion("Asistente, Presentacion de trabajo");
        inscripcion.setFechaPago(LocalDate.now());

        persistir(em, inscripcion);
        Long id = id(inscripcion);
        out.println("Alta OK. Id: " + id);

        Inscripcion recuperada = em.find(Inscripcion.class, id);
        out.println("Recuperacion OK. Estado: " + recuperada.getEstado());

        modificar(em, () -> recuperada.setEstado(EstadoPago.APROBADO));
        out.println("Modificacion OK. Estado: " + em.find(Inscripcion.class, id).getEstado());

        eliminar(em, recuperada);
        out.println(em.find(Inscripcion.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDatosFactura(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] DatosFactura");

        Inscripcion inscripcion = crearInscripcionPersistida(em, "factura");
        DatosFactura datos = new DatosFactura("Facundo SRL", "20123456789", "Responsable Inscripto", "Calle 1 123", email("factura"));
        datos.setInscripcion(inscripcion);

        persistir(em, datos);
        Long id = id(datos);
        out.println("Alta OK. Id: " + id);

        DatosFactura recuperado = em.find(DatosFactura.class, id);
        out.println("Recuperacion OK. CUIT: " + recuperado.getCuit());

        modificar(em, () -> recuperado.setOtrosDatos("Otros datos modificados"));
        out.println("Modificacion OK. Otros datos: " + em.find(DatosFactura.class, id).getOtrosDatos());

        eliminar(em, recuperado);
        out.println(em.find(DatosFactura.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testQR(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] QR");

        Inscripcion inscripcion = crearInscripcionPersistida(em, "qr");
        QR qr = new QR("QR-" + sufijo, inscripcion);

        persistir(em, qr);
        Long id = id(qr);
        out.println("Alta OK. Id: " + id);

        QR recuperado = em.find(QR.class, id);
        out.println("Recuperacion OK. Codigo: " + recuperado.getCodigo());

        modificar(em, () -> recuperado.setUtilizado(true));
        out.println("Modificacion OK. Utilizado: " + em.find(QR.class, id).getUtilizado());

        eliminar(em, recuperado);
        out.println(em.find(QR.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testAsignacionEvaluacion(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] AsignacionEvaluacion");

        Trabajo trabajo = crearTrabajoPersistido(em, "Trabajo Asignacion");
        Usuario evaluador = crearUsuarioPersistido(em, "evaluador_asignacion");
        Usuario asignador = crearUsuarioPersistido(em, "asignador_asignacion");
        AsignacionEvaluacion asignacion = new AsignacionEvaluacion(trabajo, evaluador, asignador);

        persistir(em, asignacion);
        Long id = id(asignacion);
        out.println("Alta OK. Id: " + id);

        AsignacionEvaluacion recuperada = em.find(AsignacionEvaluacion.class, id);
        out.println("Recuperacion OK. Estado: " + recuperada.getEstado());

        modificar(em, () -> recuperada.setEstado(EstadoAsignacion.ACEPTADA));
        out.println("Modificacion OK. Estado: " + em.find(AsignacionEvaluacion.class, id).getEstado());

        eliminar(em, recuperada);
        out.println(em.find(AsignacionEvaluacion.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEvaluacion(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Evaluacion");

        AsignacionEvaluacion asignacion = crearAsignacionPersistida(em, "evaluacion");
        Evaluacion evaluacion = new Evaluacion(asignacion, Recomendacion.APROBADO_CON_CORRECCION, "Comentarios para autores");
        evaluacion.setPertinenteCongreso(true);
        evaluacion.setEjeTematicoCorrecto(true);
        evaluacion.setTipoTrabajoEvaluado(TipoTrabajo.TRABAJO_CIENTIFICO);
        evaluacion.setModalidadRecomendada(ModalidadPresentacion.POSTER);
        evaluacion.setComentariosComision("Comentario privado");

        persistir(em, evaluacion);
        Long id = id(evaluacion);
        out.println("Alta OK. Id: " + id);

        Evaluacion recuperada = em.find(Evaluacion.class, id);
        out.println("Recuperacion OK. Recomendacion: " + recuperada.getRecomendacion());

        modificar(em, () -> recuperada.setComentariosAutores("Comentarios modificados"));
        out.println("Modificacion OK. Comentarios: " + em.find(Evaluacion.class, id).getComentariosAutores());

        eliminar(em, recuperada);
        out.println(em.find(Evaluacion.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testDecisionTrabajo(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] DecisionTrabajo");

        Trabajo trabajo = crearTrabajoPersistido(em, "Trabajo Decision");
        Usuario organizador = crearUsuarioPersistido(em, "organizador_decision");
        DecisionTrabajo decision = new DecisionTrabajo(trabajo, organizador, EstadoTrabajo.ACEPTADO, "Decision inicial");

        persistir(em, decision);
        Long id = id(decision);
        out.println("Alta OK. Id: " + id);

        DecisionTrabajo recuperada = em.find(DecisionTrabajo.class, id);
        out.println("Recuperacion OK. Decision: " + recuperada.getDecision());

        modificar(em, () -> recuperada.setComentario("Decision modificada"));
        out.println("Modificacion OK. Comentario: " + em.find(DecisionTrabajo.class, id).getComentario());

        eliminar(em, recuperada);
        out.println(em.find(DecisionTrabajo.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCronograma(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Cronograma");

        Cronograma cronograma = new Cronograma();
        persistir(em, cronograma);
        Long id = id(cronograma);
        out.println("Alta OK. Id: " + id);

        Cronograma recuperado = em.find(Cronograma.class, id);
        out.println("Recuperacion OK. Actividades: " + recuperado.getActividades().size());

        modificar(em, () -> recuperado.setActividades(recuperado.getActividades()));
        out.println("Modificacion OK.");

        eliminar(em, recuperado);
        out.println(em.find(Cronograma.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testActividad(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Actividad");

        Cronograma cronograma = crearCronogramaPersistido(em);
        Actividad actividad = nuevaActividad("Actividad ABM");
        actividad.setCronograma(cronograma);

        persistir(em, actividad);
        Long id = id(actividad);
        out.println("Alta OK. Id: " + id);

        Actividad recuperada = em.find(Actividad.class, id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        modificar(em, () -> recuperada.setAula("Aula modificada"));
        out.println("Modificacion OK. Aula: " + em.find(Actividad.class, id).getAula());

        eliminar(em, recuperada);
        out.println(em.find(Actividad.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testAgenda(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Agenda");

        Usuario usuario = crearUsuarioPersistido(em, "agenda_usuario");
        Agenda agenda = new Agenda(usuario);

        persistir(em, agenda);
        Long id = id(agenda);
        out.println("Alta OK. Id: " + id);

        Agenda recuperada = em.find(Agenda.class, id);
        out.println("Recuperacion OK. Usuario: " + recuperada.getUsuario().getEmail());

        modificar(em, () -> recuperada.setItems(recuperada.getItems()));
        out.println("Modificacion OK.");

        eliminar(em, recuperada);
        out.println(em.find(Agenda.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testItemAgenda(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] ItemAgenda");

        Usuario usuario = crearUsuarioPersistido(em, "item_agenda_usuario");
        Agenda agenda = crearAgendaPersistida(em, usuario);
        Actividad actividad = crearActividadPersistida(em, "Actividad ItemAgenda");
        ItemAgenda item = new ItemAgenda(agenda, actividad);

        persistir(em, item);
        Long id = id(item);
        out.println("Alta OK. Id: " + id);

        ItemAgenda recuperado = em.find(ItemAgenda.class, id);
        out.println("Recuperacion OK. Actividad: " + recuperado.getActividad().getTitulo());

        modificar(em, () -> recuperado.setActividad(actividad));
        out.println("Modificacion OK.");

        eliminar(em, recuperado);
        out.println(em.find(ItemAgenda.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCongreso(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Congreso");

        Cronograma cronograma = crearCronogramaPersistido(em);
        Congreso congreso = new Congreso("Congreso ABM", "V");
        congreso.setFechaInicio(LocalDate.now());
        congreso.setFechaFin(LocalDate.now().plusDays(3));
        congreso.setCronograma(cronograma);

        persistir(em, congreso);
        Long id = id(congreso);
        out.println("Alta OK. Id: " + id);

        Congreso recuperado = em.find(Congreso.class, id);
        out.println("Recuperacion OK. Nombre: " + recuperado.getNombre());

        modificar(em, () -> recuperado.setNombre("Congreso modificado"));
        out.println("Modificacion OK. Nombre: " + em.find(Congreso.class, id).getNombre());

        eliminar(em, recuperado);
        out.println(em.find(Congreso.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testEtapaCongreso(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] EtapaCongreso");

        Congreso congreso = crearCongresoPersistido(em, "Congreso Etapa");
        EtapaCongreso etapa = new EtapaCongreso("Recepcion de trabajos", LocalDate.now(), LocalDate.now().plusDays(30));
        etapa.setCongreso(congreso);

        persistir(em, etapa);
        Long id = id(etapa);
        out.println("Alta OK. Id: " + id);

        EtapaCongreso recuperada = em.find(EtapaCongreso.class, id);
        out.println("Recuperacion OK. Nombre: " + recuperada.getNombre());

        modificar(em, () -> recuperada.setNombre("Etapa modificada"));
        out.println("Modificacion OK. Nombre: " + em.find(EtapaCongreso.class, id).getNombre());

        eliminar(em, recuperada);
        out.println(em.find(EtapaCongreso.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testCircular(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Circular");

        Usuario usuario = crearUsuarioPersistido(em, "circular_publicador");
        Circular circular = new Circular("Circular ABM", "circular.pdf", usuario);
        circular.setFechaPublicacion(LocalDate.now());

        persistir(em, circular);
        Long id = id(circular);
        out.println("Alta OK. Id: " + id);

        Circular recuperada = em.find(Circular.class, id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        modificar(em, () -> recuperada.setTitulo("Circular modificada"));
        out.println("Modificacion OK. Titulo: " + em.find(Circular.class, id).getTitulo());

        eliminar(em, recuperada);
        out.println(em.find(Circular.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testNoticia(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Noticia");

        Usuario usuario = crearUsuarioPersistido(em, "noticia_publicador");
        Noticia noticia = new Noticia("Noticia ABM", "Contenido de prueba", usuario);
        noticia.setFechaPublicacion(LocalDate.now());

        persistir(em, noticia);
        Long id = id(noticia);
        out.println("Alta OK. Id: " + id);

        Noticia recuperada = em.find(Noticia.class, id);
        out.println("Recuperacion OK. Titulo: " + recuperada.getTitulo());

        modificar(em, () -> recuperada.setContenido("Contenido modificado"));
        out.println("Modificacion OK. Contenido: " + em.find(Noticia.class, id).getContenido());

        eliminar(em, recuperada);
        out.println(em.find(Noticia.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testPlantillaEmail(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] PlantillaEmail");

        PlantillaEmail plantilla = new PlantillaEmail("Asunto ABM", "Cuerpo de prueba");
        persistir(em, plantilla);
        Long id = id(plantilla);
        out.println("Alta OK. Id: " + id);

        PlantillaEmail recuperada = em.find(PlantillaEmail.class, id);
        out.println("Recuperacion OK. Asunto: " + recuperada.getAsunto());

        modificar(em, () -> recuperada.setCuerpo("Cuerpo modificado"));
        out.println("Modificacion OK. Cuerpo: " + em.find(PlantillaEmail.class, id).getCuerpo());

        eliminar(em, recuperada);
        out.println(em.find(PlantillaEmail.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
    }

    private void testNotificacion(EntityManager em, PrintWriter out) {
        out.println("\n[TEST] Notificacion");

        Usuario destinatario = crearUsuarioPersistido(em, "notificacion_destinatario");
        PlantillaEmail plantilla = crearPlantillaPersistida(em);
        Notificacion notificacion = new Notificacion("Notificacion ABM", "Mensaje de prueba", destinatario);
        notificacion.setPlantilla(plantilla);
        notificacion.setFechaEnvio(LocalDate.now());

        persistir(em, notificacion);
        Long id = id(notificacion);
        out.println("Alta OK. Id: " + id);

        Notificacion recuperada = em.find(Notificacion.class, id);
        out.println("Recuperacion OK. Asunto: " + recuperada.getAsunto());

        modificar(em, () -> recuperada.setMensaje("Mensaje modificado"));
        out.println("Modificacion OK. Mensaje: " + em.find(Notificacion.class, id).getMensaje());

        eliminar(em, recuperada);
        out.println(em.find(Notificacion.class, id) == null ? "Baja OK." : "Baja FALLIDA.");
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

    private Usuario crearUsuarioPersistido(EntityManager em, String prefijo) {
        Usuario usuario = nuevoUsuario(prefijo);
        persistir(em, usuario);
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

    private Trabajo crearTrabajoPersistido(EntityManager em, String titulo) {
        Usuario autor = crearUsuarioPersistido(em, "autor_" + titulo.replace(" ", "_"));
        Trabajo trabajo = nuevoTrabajo(titulo);
        trabajo.setAutorContacto(autor);
        persistir(em, trabajo);
        return trabajo;
    }

    private EjeTematico nuevoEje(String nombre) {
        return new EjeTematico(nombre + " " + sufijo, "Descripcion de prueba");
    }

    private EjeTematico crearEjePersistido(EntityManager em, String nombre) {
        EjeTematico eje = nuevoEje(nombre);
        persistir(em, eje);
        return eje;
    }

    private PerfilEvaluador crearPerfilEvaluadorPersistido(EntityManager em, String prefijo) {
        Usuario usuario = crearUsuarioPersistido(em, prefijo);
        PerfilEvaluador perfil = new PerfilEvaluador(usuario);
        perfil.setFormacionAcademica("Curso de posgrado");
        perfil.setAreasConocimiento("Agricultura urbana");
        perfil.setSubareasConocimiento("Manejo de suelo");
        persistir(em, perfil);
        return perfil;
    }

    private Inscripcion crearInscripcionPersistida(EntityManager em, String prefijo) {
        Usuario usuario = crearUsuarioPersistido(em, "usuario_" + prefijo);
        Inscripcion inscripcion = new Inscripcion(usuario, 15000.0, "comprobante_" + prefijo + ".pdf");
        inscripcion.setFechaPago(LocalDate.now());
        persistir(em, inscripcion);
        return inscripcion;
    }

    private AsignacionEvaluacion crearAsignacionPersistida(EntityManager em, String prefijo) {
        Trabajo trabajo = crearTrabajoPersistido(em, "Trabajo " + prefijo);
        Usuario evaluador = crearUsuarioPersistido(em, "evaluador_" + prefijo);
        Usuario asignador = crearUsuarioPersistido(em, "asignador_" + prefijo);
        AsignacionEvaluacion asignacion = new AsignacionEvaluacion(trabajo, evaluador, asignador);
        persistir(em, asignacion);
        return asignacion;
    }

    private Cronograma crearCronogramaPersistido(EntityManager em) {
        Cronograma cronograma = new Cronograma();
        persistir(em, cronograma);
        return cronograma;
    }

    private Actividad nuevaActividad(String titulo) {
        Actividad actividad = new Actividad(titulo + " " + sufijo, "Aula 1", LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(10, 0));
        actividad.setDescripcion("Descripcion de actividad");
        return actividad;
    }

    private Actividad crearActividadPersistida(EntityManager em, String titulo) {
        Cronograma cronograma = crearCronogramaPersistido(em);
        Actividad actividad = nuevaActividad(titulo);
        actividad.setCronograma(cronograma);
        persistir(em, actividad);
        return actividad;
    }

    private Agenda crearAgendaPersistida(EntityManager em, Usuario usuario) {
        Agenda agenda = new Agenda(usuario);
        persistir(em, agenda);
        return agenda;
    }

    private Congreso crearCongresoPersistido(EntityManager em, String nombre) {
        Cronograma cronograma = crearCronogramaPersistido(em);
        Congreso congreso = new Congreso(nombre + " " + sufijo, "V");
        congreso.setFechaInicio(LocalDate.now());
        congreso.setFechaFin(LocalDate.now().plusDays(3));
        congreso.setCronograma(cronograma);
        persistir(em, congreso);
        return congreso;
    }

    private PlantillaEmail crearPlantillaPersistida(EntityManager em) {
        PlantillaEmail plantilla = new PlantillaEmail("Asunto plantilla " + sufijo, "Cuerpo plantilla");
        persistir(em, plantilla);
        return plantilla;
    }

    private Rol obtenerOCrearRol(EntityManager em, TipoRol tipoRol) {
        return em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :nombre", Rol.class)
                .setParameter("nombre", tipoRol)
                .getResultStream()
                .findFirst()
                .orElseGet(() -> {
                    Rol rol = new Rol(tipoRol);
                    persistir(em, rol);
                    return rol;
                });
    }

    private void persistir(EntityManager em, Object entidad) {
        em.getTransaction().begin();
        em.persist(entidad);
        em.getTransaction().commit();
    }

    private void modificar(EntityManager em, Runnable cambios) {
        em.getTransaction().begin();
        cambios.run();
        em.getTransaction().commit();
    }

    private void eliminar(EntityManager em, Object entidad) {
        em.getTransaction().begin();
        Object gestionada = em.contains(entidad) ? entidad : em.merge(entidad);
        em.remove(gestionada);
        em.getTransaction().commit();
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