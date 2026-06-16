package model;

import jakarta.persistence.*;
import model.enums.EstadoAsignacion;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "asignaciones_evaluacion")
public class AsignacionEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id")
    @JsonIgnore
    private Trabajo trabajo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluador_id")
    private Usuario evaluador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asignado_por_id")
    private Usuario asignadoPor;

    private LocalDate fechaAsignacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
