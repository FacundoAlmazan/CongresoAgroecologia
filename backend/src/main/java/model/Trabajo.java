package model;

import jakarta.persistence.*;
import model.enums.EstadoTrabajo;
import model.enums.TipoTrabajo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trabajos")
public class Trabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 5000)
    private String resumen;

    private String archivo;

    private LocalDate fechaEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTrabajo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTrabajo estado = EstadoTrabajo.BORRADOR;

    @OneToMany(mappedBy = "trabajo")
    private List<AsignacionEvaluacion> asignaciones = new ArrayList<>();

    @OneToMany(mappedBy = "trabajo")
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    @OneToOne(mappedBy = "trabajo")
    private DecisionTrabajo decisionTrabajo;

    public Trabajo() {}

    public Trabajo(String titulo, String resumen, TipoTrabajo tipo) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.tipo = tipo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    public String getArchivo() { return archivo; }
    public void setArchivo(String archivo) { this.archivo = archivo; }
    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public TipoTrabajo getTipo() { return tipo; }
    public void setTipo(TipoTrabajo tipo) { this.tipo = tipo; }
    public EstadoTrabajo getEstado() { return estado; }
    public void setEstado(EstadoTrabajo estado) { this.estado = estado; }
    public List<AsignacionEvaluacion> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionEvaluacion> asignaciones) { this.asignaciones = asignaciones; }
    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
    public DecisionTrabajo getDecisionTrabajo() { return decisionTrabajo; }
    public void setDecisionTrabajo(DecisionTrabajo decisionTrabajo) { this.decisionTrabajo = decisionTrabajo; }
}
