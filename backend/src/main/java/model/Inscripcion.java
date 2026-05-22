package model;

import model.enums.EstadoPago;
import java.time.LocalDate;

public class Inscripcion {
    private Long id;

    private Usuario usuario;

    private Double monto;

    private LocalDate fechaPago;

    private String comprobante;

    private Boolean requiereFactura = false;

    private Boolean efectivo = false;

    private EstadoPago estado = EstadoPago.PENDIENTE;

    private String motivoRechazo;

    private Usuario validadoPor;

    private QR qr;

    public Inscripcion() {}
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
    public LocalDate getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDate fechaPago) { this.fechaPago = fechaPago; }
    public String getComprobante() { return comprobante; }
    public void setComprobante(String comprobante) { this.comprobante = comprobante; }
    public Boolean getRequiereFactura() { return requiereFactura; }
    public void setRequiereFactura(Boolean requiereFactura) { this.requiereFactura = requiereFactura; }
    public Boolean getEfectivo() { return efectivo; }
    public void setEfectivo(Boolean efectivo) { this.efectivo = efectivo; }
    public EstadoPago getEstado() { return estado; }
    public void setEstado(EstadoPago estado) { this.estado = estado; }
    public String getMotivoRechazo() { return motivoRechazo; }
    public void setMotivoRechazo(String motivoRechazo) { this.motivoRechazo = motivoRechazo; }
    public Usuario getValidadoPor() { return validadoPor; }
    public void setValidadoPor(Usuario validadoPor) { this.validadoPor = validadoPor; }
    public QR getQr() { return qr; }
    public void setQr(QR qr) { this.qr = qr; }
}
