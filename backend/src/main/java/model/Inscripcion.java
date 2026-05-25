package model;

import jakarta.persistence.*;
import model.enums.EstadoPago;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private Double monto;

    private LocalDate fechaPago;

    private String comprobante;

    @Column(nullable = false)
    private Boolean requiereFactura = false;

    @Column(nullable = false)
    private Boolean efectivo = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado = EstadoPago.PENDIENTE;

    @Column(length = 1000)
    private String motivoRechazo;

    @ManyToOne
    @JoinColumn(name = "validado_por_id")
    private Usuario validadoPor;

    @OneToOne(mappedBy = "inscripcion")
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
