package model;

import jakarta.persistence.Column;
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
    @Schema(description = "Usuario participante. En POST alcanza con enviar {\"id\": 1}.")
    private Usuario usuario;

    @Column(nullable = false)
    @Schema(example = "15000")
    private Double monto;

    @Schema(type = "string", format = "date", example = "2026-06-17")
    private LocalDate fechaPago;

    @Schema(example = "comprobante-transferencia.pdf")
    private String comprobante;
    @Schema(example = "acreditacion.pdf")
    private String comprobanteAcreditacion;

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

    @OneToOne(mappedBy = "inscripcion")
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

    @ManyToOne
    @JoinColumn(name = "validado_por_id")
    private Usuario validadoPor;

    @OneToOne(mappedBy = "inscripcion")
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
    }

    public void rechazar(Usuario adminValidador, String motivo) {
        this.estado = EstadoPago.RECHAZADO;
        this.validadoPor = adminValidador;
        this.motivoRechazo = motivo;
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
    }

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

    public String getComprobanteAcreditacion() {
        return comprobanteAcreditacion;
    }

    public void setComprobanteAcreditacion(String comprobanteAcreditacion) {
        this.comprobanteAcreditacion = comprobanteAcreditacion;
    }

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
    }
}
