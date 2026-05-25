package model;

import jakarta.persistence.*;
import model.enums.Recomendacion;
import java.time.LocalDate;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Recomendacion recomendacion;

    @Column(length = 3000)
    private String comentarios;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id")
    private Trabajo trabajo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluador_id")
    private Usuario evaluador;


    public Evaluacion() {}
    public Evaluacion(Trabajo trabajo, Usuario evaluador, Recomendacion recomendacion, String comentarios) {
        this.trabajo = trabajo;
        this.evaluador = evaluador;
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
}
