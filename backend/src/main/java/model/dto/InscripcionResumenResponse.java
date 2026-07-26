package model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Inscripcion;
import model.enums.EstadoPago;

public class InscripcionResumenResponse {
    private Long id;
    private UsuarioResumenResponse usuario;
    private Double monto;
    private Double montoAbonado;
    private Double diferenciaMonto;
    private LocalDate fechaPago;
    private LocalDateTime fechaRegistro;
    private String tipoInscripcion;
    private EstadoPago estado;

    public InscripcionResumenResponse() {}

    public static InscripcionResumenResponse desde(Inscripcion inscripcion) {
        InscripcionResumenResponse response = new InscripcionResumenResponse();
        response.id = inscripcion.getId();
        response.usuario = UsuarioResumenResponse.desde(inscripcion.getUsuario());
        response.monto = inscripcion.getMonto();
        response.montoAbonado = inscripcion.getMontoAbonado();
        response.diferenciaMonto = inscripcion.getDiferenciaMonto();
        response.fechaPago = inscripcion.getFechaPago();
        response.fechaRegistro = inscripcion.getFechaRegistro();
        response.tipoInscripcion = inscripcion.getTipoInscripcion();
        response.estado = inscripcion.getEstado();
        return response;
    }

    public Long getId() { return id; }
    public UsuarioResumenResponse getUsuario() { return usuario; }
    public Double getMonto() { return monto; }
    public Double getMontoAbonado() { return montoAbonado; }
    public Double getDiferenciaMonto() { return diferenciaMonto; }
    public LocalDate getFechaPago() { return fechaPago; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public String getTipoInscripcion() { return tipoInscripcion; }
    public EstadoPago getEstado() { return estado; }
}
