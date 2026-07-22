package model;

import jakarta.persistence.*;
import model.enums.EstadoAsignacion;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "asignaciones_evaluacion")
public class AsignacionEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;
    
    public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	private boolean finalizado=false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id")
    @JsonIgnoreProperties({"asignaciones", "evaluaciones"})
    @Schema(description = "Trabajo existente. En POST alcanza con enviar {\"id\": 1}.")
    private Trabajo trabajo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluador_id")
    @Schema(description = "Usuario evaluador existente. En POST alcanza con enviar {\"id\": 2}.")
    private Usuario evaluador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asignado_por_id")
    @Schema(description = "Usuario OC/administrador que realiza la asignación. En POST alcanza con enviar {\"id\": 1}.")
    private Usuario asignadoPor;

    @Schema(type = "string", format = "date", example = "2026-06-17")
    private LocalDate fechaAsignacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "PENDIENTE")
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
