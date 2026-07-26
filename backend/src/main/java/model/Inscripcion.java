package model;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import model.enums.EstadoPago;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties({"password", "perfilEvaluador", "hibernateLazyInitializer", "handler"})
    @Schema(description = "Usuario participante. En POST alcanza con enviar {\"id\": 1}.")
    private Usuario usuario;

    @Column(nullable = false)
    @Schema(example = "15000")
    private Double monto;

    private Double montoAbonado = 0.0;

    private Double diferenciaMonto = 0.0;

    @Schema(type = "string", format = "date", example = "2026-06-17")
    private LocalDate fechaPago;

    private String comprobante;

    @jakarta.persistence.Lob
    @Column(columnDefinition = "LONGTEXT")
    private String comprobanteContenido;

    private String comprobanteAcreditacion;

    @jakarta.persistence.Lob
    @Column(columnDefinition = "LONGTEXT")
    private String comprobanteAcreditacionContenido;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(length = 500)
    @Schema(example = "PARTICIPANTE")
    private String tipoInscripcion;

    @Column(length = 500)
    @Schema(example = "UNLP")
    private String filiacionInstitucional;

    @Column(length = 1000)
    @Schema(example = "Asistente")
    private String formasParticipacion;

    @Column(nullable = false)
    @Schema(example = "true")
    private Boolean requiereFactura = false;

    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("inscripcion")
    private DatosFactura datosFactura;

    @Column(nullable = false)
    private Boolean requiereAdecuacion = false;

    @Column(length = 2000)
    private String detalleAdecuacion;

    @Column(nullable = false)
    private Boolean tieneAlergiaOIntolerancia = false;

    @Column(length = 2000)
    private String detalleAlergiaOIntolerancia;

    @Column(length = 2000)
    private String observaciones;

    @Column(nullable = false)
    @Schema(example = "false")
    private Boolean efectivo = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "PENDIENTE")
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @Column(length = 1000)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "Comprobante ilegible")
    private String motivoRechazo;

    @Column(length = 2000)
    private String comentarioRevision;

    @ManyToOne
    @JoinColumn(name = "validado_por_id")
    @JsonIgnoreProperties({"password", "perfilEvaluador", "hibernateLazyInitializer", "handler"})
    private Usuario validadoPor;

    @OneToOne(mappedBy = "inscripcion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("inscripcion")
    private QR qr;

    public Inscripcion() {
    }

    public Inscripcion(Usuario usuario, Double monto, String comprobante) {
        this.usuario = usuario;
        this.monto = monto;
        this.comprobante = comprobante;
    }

    public void aprobar(Usuario adminValidador) {
        this.estado = EstadoPago.APROBADO;
        this.validadoPor = adminValidador;
        this.motivoRechazo = null;
        this.comentarioRevision = null;
        if (this.qr == null) {
            setQr(new QR(UUID.randomUUID().toString(), this));
        }
    }

    public void observar(Usuario adminValidador, String comentario, Double montoAbonado) {
        actualizarMontoAbonado(montoAbonado);
        this.estado = EstadoPago.OBSERVADO;
        this.validadoPor = adminValidador;
        this.comentarioRevision = comentario;
        this.motivoRechazo = null;
    }

    public void rechazar(Usuario adminValidador, String motivo) {
        this.estado = EstadoPago.RECHAZADO;
        this.validadoPor = adminValidador;
        this.motivoRechazo = motivo;
        this.comentarioRevision = motivo;
    }

    public void reenviar() {
        this.estado = EstadoPago.PENDIENTE;
        this.validadoPor = null;
        this.motivoRechazo = null;
        this.comentarioRevision = null;
    }

    public void actualizarMontoAbonado(Double montoAbonado) {
        this.montoAbonado = montoAbonado == null ? 0.0 : montoAbonado;
        this.diferenciaMonto = Math.max(0.0, (this.monto == null ? 0.0 : this.monto) - this.montoAbonado);
    }

    public void asignarDatosFactura(DatosFactura datosFactura) {
        this.datosFactura = datosFactura;
        if (datosFactura != null) {
            datosFactura.setInscripcion(this);
            this.requiereFactura = true;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
        actualizarMontoAbonado(this.montoAbonado);
    }

    public Double getMontoAbonado() { return montoAbonado; }
    public void setMontoAbonado(Double montoAbonado) { actualizarMontoAbonado(montoAbonado); }
    public Double getDiferenciaMonto() { return diferenciaMonto; }
    public void setDiferenciaMonto(Double diferenciaMonto) { this.diferenciaMonto = diferenciaMonto; }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getComprobanteContenido() { return comprobanteContenido; }
    public void setComprobanteContenido(String comprobanteContenido) { this.comprobanteContenido = comprobanteContenido; }

    public String getComprobanteAcreditacion() {
        return comprobanteAcreditacion;
    }

    public void setComprobanteAcreditacion(String comprobanteAcreditacion) {
        this.comprobanteAcreditacion = comprobanteAcreditacion;
    }

    public String getComprobanteAcreditacionContenido() { return comprobanteAcreditacionContenido; }
    public void setComprobanteAcreditacionContenido(String contenido) { this.comprobanteAcreditacionContenido = contenido; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getTipoInscripcion() {
        return tipoInscripcion;
    }

    public void setTipoInscripcion(String tipoInscripcion) {
        this.tipoInscripcion = tipoInscripcion;
    }

    public String getFiliacionInstitucional() {
        return filiacionInstitucional;
    }

    public void setFiliacionInstitucional(String filiacionInstitucional) {
        this.filiacionInstitucional = filiacionInstitucional;
    }

    public String getFormasParticipacion() {
        return formasParticipacion;
    }

    public void setFormasParticipacion(String formasParticipacion) {
        this.formasParticipacion = formasParticipacion;
    }

    public Boolean getRequiereFactura() {
        return requiereFactura;
    }

    public void setRequiereFactura(Boolean requiereFactura) {
        this.requiereFactura = requiereFactura;
    }

    public DatosFactura getDatosFactura() {
        return datosFactura;
    }

    public void setDatosFactura(DatosFactura datosFactura) {
        this.datosFactura = datosFactura;
        if (datosFactura != null) {
            datosFactura.setInscripcion(this);
            this.requiereFactura = true;
        }
    }

    public Boolean getRequiereAdecuacion() {
        return requiereAdecuacion;
    }

    public void setRequiereAdecuacion(Boolean requiereAdecuacion) {
        this.requiereAdecuacion = requiereAdecuacion;
    }

    public String getDetalleAdecuacion() {
        return detalleAdecuacion;
    }

    public void setDetalleAdecuacion(String detalleAdecuacion) {
        this.detalleAdecuacion = detalleAdecuacion;
    }

    public Boolean getTieneAlergiaOIntolerancia() {
        return tieneAlergiaOIntolerancia;
    }

    public void setTieneAlergiaOIntolerancia(Boolean tieneAlergiaOIntolerancia) {
        this.tieneAlergiaOIntolerancia = tieneAlergiaOIntolerancia;
    }

    public String getDetalleAlergiaOIntolerancia() {
        return detalleAlergiaOIntolerancia;
    }

    public void setDetalleAlergiaOIntolerancia(String detalleAlergiaOIntolerancia) {
        this.detalleAlergiaOIntolerancia = detalleAlergiaOIntolerancia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Boolean efectivo) {
        this.efectivo = efectivo;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public String getComentarioRevision() { return comentarioRevision; }
    public void setComentarioRevision(String comentarioRevision) { this.comentarioRevision = comentarioRevision; }

    public Usuario getValidadoPor() {
        return validadoPor;
    }

    public void setValidadoPor(Usuario validadoPor) {
        this.validadoPor = validadoPor;
    }

    public QR getQr() {
        return qr;
    }

    public void setQr(QR qr) {
        this.qr = qr;
        if (qr != null) {
            qr.setInscripcion(this);
        }
    }
}
