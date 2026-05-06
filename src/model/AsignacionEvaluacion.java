package model;

import model.enums.EstadoAsignacion;
import java.time.LocalDate;

public class AsignacionEvaluacion {
    private Long id;

    private Trabajo trabajo;

    private Usuario evaluador;

    private Usuario asignadoPor;

    private LocalDate fechaAsignacion;

    private EstadoAsignacion estado = EstadoAsignacion.PENDIENTE;

    public AsignacionEvaluacion() {}
    public AsignacionEvaluacion(Trabajo trabajo, Usuario evaluador, Usuario asignadoPor) {
        this.trabajo = trabajo;
        this.evaluador = evaluador;
        this.asignadoPor = asignadoPor;
        this.fechaAsignacion = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
    public Usuario getEvaluador() { return evaluador; }
    public void setEvaluador(Usuario evaluador) { this.evaluador = evaluador; }
    public Usuario getAsignadoPor() { return asignadoPor; }
    public void setAsignadoPor(Usuario asignadoPor) { this.asignadoPor = asignadoPor; }
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    public EstadoAsignacion getEstado() { return estado; }
    public void setEstado(EstadoAsignacion estado) { this.estado = estado; }
}
