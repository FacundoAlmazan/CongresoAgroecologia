package model;

import model.enums.Recomendacion;
import java.time.LocalDate;

public class Evaluacion {
    private Long id;

    private LocalDate fecha;

    private Recomendacion recomendacion;

    private String comentarios;

    private Trabajo trabajo;

    private Usuario evaluador;

    public Evaluacion() {}
    public Evaluacion(AsignacionEvaluacion asignacion, Recomendacion recomendacion, String comentarios) {
        this.asignacion = asignacion;
        this.trabajo = asignacion.getTrabajo();
        this.evaluador = asignacion.getEvaluador();
        this.recomendacion = recomendacion;
        this.comentarios = comentarios;
        this.fecha = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Recomendacion getRecomendacion() { return recomendacion; }
    public void setRecomendacion(Recomendacion recomendacion) { this.recomendacion = recomendacion; }
    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
    public Usuario getEvaluador() { return evaluador; }
    public void setEvaluador(Usuario evaluador) { this.evaluador = evaluador; }
    public AsignacionEvaluacion getAsignacion() { return asignacion; }
    public void setAsignacion(AsignacionEvaluacion asignacion) { this.asignacion = asignacion; }
}
