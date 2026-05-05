package model;

import model.enums.EstadoTrabajo;
import java.time.LocalDate;

public class DecisionTrabajo {
    private Long id;

    private LocalDate fecha;

    private EstadoTrabajo decision;

    private String comentario;

    private Trabajo trabajo;

    private Usuario organizadorCientifico;

    public DecisionTrabajo() {}
    public DecisionTrabajo(Trabajo trabajo, Usuario organizadorCientifico, EstadoTrabajo decision, String comentario) {
        this.trabajo = trabajo;
        this.organizadorCientifico = organizadorCientifico;
        this.decision = decision;
        this.comentario = comentario;
        this.fecha = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public EstadoTrabajo getDecision() { return decision; }
    public void setDecision(EstadoTrabajo decision) { this.decision = decision; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
    public Usuario getOrganizadorCientifico() { return organizadorCientifico; }
    public void setOrganizadorCientifico(Usuario organizadorCientifico) { this.organizadorCientifico = organizadorCientifico; }
}
