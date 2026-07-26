package model.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import model.Inscripcion;
import model.Usuario;
import model.interfaceDao.IInscripcionDAO;
import model.interfaceDao.IUsuarioDAO;
import model.enums.EstadoPago;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequestScoped
public class PagoService {

    @Inject
    private IInscripcionDAO inscripcionDAO;

    @Inject
    private IUsuarioDAO usuarioDAO;

    public List<Inscripcion> listar(EstadoPago estado) {
        return estado == null
                ? inscripcionDAO.listarTodos()
                : inscripcionDAO.getInscripcionesByEstadoPago(estado);
    }

    public Inscripcion obtener(Long id) {
        Inscripcion inscripcion = inscripcionDAO.getByID(id);
        if (inscripcion == null) {
            throw new NotFoundException("Pago/inscripción no encontrado con id " + id);
        }
        return inscripcion;
    }

    public Inscripcion obtenerMia(Long usuarioId) {
        return inscripcionDAO.getUltimaPorUsuario(usuarioId);
    }

    public Inscripcion registrar(Inscripcion inscripcion, Long usuarioId) {
        if (inscripcion == null) {
            throw new BadRequestException("Debe enviar los datos del pago");
        }
        Usuario usuario = obtenerUsuario(usuarioId);
        Inscripcion existente = inscripcionDAO.getUltimaPorUsuario(usuarioId);
        if (existente != null && existente.getEstado() != EstadoPago.RECHAZADO) {
            throw new BadRequestException("Ya existe una inscripción activa para este usuario");
        }
        validarDatos(inscripcion);
        inscripcion.setId(null);
        inscripcion.setUsuario(usuario);
        inscripcion.setEstado(EstadoPago.PENDIENTE);
        inscripcion.setValidadoPor(null);
        inscripcion.setMotivoRechazo(null);
        inscripcion.setComentarioRevision(null);
        inscripcion.setFechaRegistro(java.time.LocalDateTime.now());
        if (!Boolean.TRUE.equals(inscripcion.getEfectivo()) && inscripcion.getMontoAbonado() == null) {
            inscripcion.setMontoAbonado(inscripcion.getMonto());
        }
        if (inscripcion.getDatosFactura() != null) {
            inscripcion.setDatosFactura(inscripcion.getDatosFactura());
        }
        inscripcionDAO.guardar(inscripcion);
        return inscripcion;
    }

    public Inscripcion aprobar(Long id, Long adminId, Double montoAbonado) {
        Inscripcion inscripcion = obtener(id);
        Usuario admin = obtenerUsuario(adminId);
        if (montoAbonado != null) {
            inscripcion.actualizarMontoAbonado(montoAbonado);
        }
        if (inscripcion.getDiferenciaMonto() != null && inscripcion.getDiferenciaMonto() > 0) {
            throw new BadRequestException("No se puede aprobar mientras exista una diferencia de monto");
        }
        inscripcion.aprobar(admin);
        inscripcion.getQr().setImagen(generarImagenQr(inscripcion.getQr().getCodigo()));
        return inscripcionDAO.modificar(inscripcion);
    }

    public Inscripcion observar(Long id, Long adminId, String comentario, Double montoAbonado) {
        if (comentario == null || comentario.isBlank()) {
            throw new BadRequestException("Debe indicar qué debe corregir el participante");
        }
        Inscripcion inscripcion = obtener(id);
        Usuario admin = obtenerUsuario(adminId);
        inscripcion.observar(admin, comentario.trim(), montoAbonado);
        return inscripcionDAO.modificar(inscripcion);
    }

    public Inscripcion rechazar(Long id, Long adminId, String motivo) {
        if (motivo == null || motivo.isBlank()) {
            throw new BadRequestException("Debe indicar el motivo de rechazo");
        }
        Inscripcion inscripcion = obtener(id);
        Usuario admin = obtenerUsuario(adminId);
        inscripcion.rechazar(admin, motivo);
        return inscripcionDAO.modificar(inscripcion);
    }

    public Inscripcion reenviar(Inscripcion datos, Long usuarioId) {
        Inscripcion inscripcion = inscripcionDAO.getUltimaPorUsuario(usuarioId);
        if (inscripcion == null) {
            throw new NotFoundException("No existe una inscripción para reenviar");
        }
        if (inscripcion.getEstado() != EstadoPago.OBSERVADO
                && inscripcion.getEstado() != EstadoPago.RECHAZADO) {
            throw new BadRequestException("La inscripción no admite correcciones en su estado actual");
        }
        validarDatos(datos);
        inscripcion.setMonto(datos.getMonto());
        inscripcion.setMontoAbonado(datos.getMontoAbonado() == null ? datos.getMonto() : datos.getMontoAbonado());
        inscripcion.setFechaPago(datos.getFechaPago());
        inscripcion.setComprobante(datos.getComprobante());
        inscripcion.setComprobanteContenido(datos.getComprobanteContenido());
        inscripcion.setComprobanteAcreditacion(datos.getComprobanteAcreditacion());
        inscripcion.setComprobanteAcreditacionContenido(datos.getComprobanteAcreditacionContenido());
        inscripcion.setTipoInscripcion(datos.getTipoInscripcion());
        inscripcion.setFormasParticipacion(datos.getFormasParticipacion());
        inscripcion.setFiliacionInstitucional(datos.getFiliacionInstitucional());
        inscripcion.setEfectivo(datos.getEfectivo());
        inscripcion.setRequiereFactura(datos.getRequiereFactura());
        inscripcion.setDatosFactura(datos.getDatosFactura());
        inscripcion.setRequiereAdecuacion(datos.getRequiereAdecuacion());
        inscripcion.setDetalleAdecuacion(datos.getDetalleAdecuacion());
        inscripcion.setTieneAlergiaOIntolerancia(datos.getTieneAlergiaOIntolerancia());
        inscripcion.setDetalleAlergiaOIntolerancia(datos.getDetalleAlergiaOIntolerancia());
        inscripcion.setObservaciones(datos.getObservaciones());
        inscripcion.reenviar();
        return inscripcionDAO.modificar(inscripcion);
    }

    private void validarDatos(Inscripcion inscripcion) {
        if (inscripcion.getMonto() == null || inscripcion.getMonto() < 0) {
            throw new BadRequestException("El monto de inscripción es obligatorio");
        }
        if (!Boolean.TRUE.equals(inscripcion.getEfectivo())
                && (inscripcion.getComprobanteContenido() == null
                    || inscripcion.getComprobanteContenido().isBlank())) {
            throw new BadRequestException("Debe adjuntar el comprobante de pago");
        }
        if (inscripcion.getTipoInscripcion() == null || inscripcion.getTipoInscripcion().isBlank()) {
            throw new BadRequestException("Debe seleccionar una categoría de inscripción");
        }
        if (Boolean.TRUE.equals(inscripcion.getRequiereFactura()) && inscripcion.getDatosFactura() == null) {
            throw new BadRequestException("Debe completar los datos de facturación");
        }
    }

    private String generarImagenQr(String codigo) {
        try {
            BitMatrix matrix = new QRCodeWriter().encode(codigo, BarcodeFormat.QR_CODE, 128, 128);
            StringBuilder svg = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 ")
                    .append(matrix.getWidth()).append(' ').append(matrix.getHeight())
                    .append("\" shape-rendering=\"crispEdges\"><rect width=\"100%\" height=\"100%\" fill=\"white\"/>");
            for (int y = 0; y < matrix.getHeight(); y++) {
                int x = 0;
                while (x < matrix.getWidth()) {
                    while (x < matrix.getWidth() && !matrix.get(x, y)) x++;
                    int inicio = x;
                    while (x < matrix.getWidth() && matrix.get(x, y)) x++;
                    if (inicio < x) {
                        svg.append("<rect x=\"").append(inicio).append("\" y=\"").append(y)
                                .append("\" width=\"").append(x - inicio)
                                .append("\" height=\"1\" fill=\"black\"/>");
                    }
                }
            }
            svg.append("</svg>");
            return "data:image/svg+xml;base64," + Base64.getEncoder().encodeToString(
                    svg.toString().getBytes(StandardCharsets.UTF_8));
        } catch (WriterException e) {
            throw new IllegalStateException("No se pudo generar el QR de acreditación", e);
        }
    }

    private Usuario obtenerUsuario(Long id) {
        if (id == null) {
            throw new BadRequestException("Debe indicar el usuario administrador");
        }
        Usuario usuario = usuarioDAO.getByID(id);
        if (usuario == null) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        return usuario;
    }
}
