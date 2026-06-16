package model;

import jakarta.persistence.*;
import model.enums.ModalidadPresentacion;
import model.enums.Recomendacion;
import model.enums.TipoTrabajo;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(nullable = false)
    private Boolean pertinenteCongreso = false;

    @Column(nullable = false)
    private Boolean identificoAutores = false;

    @Column(nullable = false)
    private Boolean conflictoIntereses = false;

    @Column(nullable = false)
    private Boolean ejeTematicoCorrecto = true;

    private Boolean lenguajeAdecuado;
    private Boolean tituloAdecuado;
    private Boolean resumenAdecuado;
    private Boolean palabrasClaveAdecuadas;
    private Boolean bibliografiaApa;
    private Boolean bibliografiaCompleta;
    private Boolean introduccionAdecuada;
    private Boolean objetivosAdecuados;
    private Boolean metodologiaAdecuada;
    private Boolean resultadosAdecuados;
    private Boolean tablasFigurasAdecuadas;
    private Boolean conclusionesAdecuadas;

    @Enumerated(EnumType.STRING)
    private TipoTrabajo tipoTrabajoEvaluado;

    @Enumerated(EnumType.STRING)
    private ModalidadPresentacion modalidadRecomendada;

    @Column(length = 3000)
    private String comentariosAutores;

    @Column(length = 3000)
    private String comentariosComision;

    private String archivoCorrecciones;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id")
    @JsonIgnore
    private Trabajo trabajo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evaluador_id")
    private Usuario evaluador;

    @OneToOne
    @JoinColumn(name = "asignacion_id", unique = true)
    private AsignacionEvaluacion asignacion;

    public Evaluacion() {}

    public Evaluacion(Trabajo trabajo, Usuario evaluador, Recomendacion recomendacion, String comentarios) {
        this.trabajo = trabajo;
        this.evaluador = evaluador;
        this.recomendacion = recomendacion;
        this.comentarios = comentarios;
        this.comentariosAutores = comentarios;
        this.fecha = LocalDate.now();
    }

    public Evaluacion(AsignacionEvaluacion asignacion, Recomendacion recomendacion, String comentariosAutores) {
        this.asignacion = asignacion;
        this.trabajo = asignacion.getTrabajo();
        this.evaluador = asignacion.getEvaluador();
        this.recomendacion = recomendacion;
        this.comentariosAutores = comentariosAutores;
        this.comentarios = comentariosAutores;
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
    public Boolean getPertinenteCongreso() { return pertinenteCongreso; }
    public void setPertinenteCongreso(Boolean pertinenteCongreso) { this.pertinenteCongreso = pertinenteCongreso; }
    public Boolean getIdentificoAutores() { return identificoAutores; }
    public void setIdentificoAutores(Boolean identificoAutores) { this.identificoAutores = identificoAutores; }
    public Boolean getConflictoIntereses() { return conflictoIntereses; }
    public void setConflictoIntereses(Boolean conflictoIntereses) { this.conflictoIntereses = conflictoIntereses; }
    public Boolean getEjeTematicoCorrecto() { return ejeTematicoCorrecto; }
    public void setEjeTematicoCorrecto(Boolean ejeTematicoCorrecto) { this.ejeTematicoCorrecto = ejeTematicoCorrecto; }
    public Boolean getLenguajeAdecuado() { return lenguajeAdecuado; }
    public void setLenguajeAdecuado(Boolean lenguajeAdecuado) { this.lenguajeAdecuado = lenguajeAdecuado; }
    public Boolean getTituloAdecuado() { return tituloAdecuado; }
    public void setTituloAdecuado(Boolean tituloAdecuado) { this.tituloAdecuado = tituloAdecuado; }
    public Boolean getResumenAdecuado() { return resumenAdecuado; }
    public void setResumenAdecuado(Boolean resumenAdecuado) { this.resumenAdecuado = resumenAdecuado; }
    public Boolean getPalabrasClaveAdecuadas() { return palabrasClaveAdecuadas; }
    public void setPalabrasClaveAdecuadas(Boolean palabrasClaveAdecuadas) { this.palabrasClaveAdecuadas = palabrasClaveAdecuadas; }
    public Boolean getBibliografiaApa() { return bibliografiaApa; }
    public void setBibliografiaApa(Boolean bibliografiaApa) { this.bibliografiaApa = bibliografiaApa; }
    public Boolean getBibliografiaCompleta() { return bibliografiaCompleta; }
    public void setBibliografiaCompleta(Boolean bibliografiaCompleta) { this.bibliografiaCompleta = bibliografiaCompleta; }
    public Boolean getIntroduccionAdecuada() { return introduccionAdecuada; }
    public void setIntroduccionAdecuada(Boolean introduccionAdecuada) { this.introduccionAdecuada = introduccionAdecuada; }
    public Boolean getObjetivosAdecuados() { return objetivosAdecuados; }
    public void setObjetivosAdecuados(Boolean objetivosAdecuados) { this.objetivosAdecuados = objetivosAdecuados; }
    public Boolean getMetodologiaAdecuada() { return metodologiaAdecuada; }
    public void setMetodologiaAdecuada(Boolean metodologiaAdecuada) { this.metodologiaAdecuada = metodologiaAdecuada; }
    public Boolean getResultadosAdecuados() { return resultadosAdecuados; }
    public void setResultadosAdecuados(Boolean resultadosAdecuados) { this.resultadosAdecuados = resultadosAdecuados; }
    public Boolean getTablasFigurasAdecuadas() { return tablasFigurasAdecuadas; }
    public void setTablasFigurasAdecuadas(Boolean tablasFigurasAdecuadas) { this.tablasFigurasAdecuadas = tablasFigurasAdecuadas; }
    public Boolean getConclusionesAdecuadas() { return conclusionesAdecuadas; }
    public void setConclusionesAdecuadas(Boolean conclusionesAdecuadas) { this.conclusionesAdecuadas = conclusionesAdecuadas; }
    public TipoTrabajo getTipoTrabajoEvaluado() { return tipoTrabajoEvaluado; }
    public void setTipoTrabajoEvaluado(TipoTrabajo tipoTrabajoEvaluado) { this.tipoTrabajoEvaluado = tipoTrabajoEvaluado; }
    public ModalidadPresentacion getModalidadRecomendada() { return modalidadRecomendada; }
    public void setModalidadRecomendada(ModalidadPresentacion modalidadRecomendada) { this.modalidadRecomendada = modalidadRecomendada; }
    public String getComentariosAutores() { return comentariosAutores; }
    public void setComentariosAutores(String comentariosAutores) { this.comentariosAutores = comentariosAutores; }
    public String getComentariosComision() { return comentariosComision; }
    public void setComentariosComision(String comentariosComision) { this.comentariosComision = comentariosComision; }
    public String getArchivoCorrecciones() { return archivoCorrecciones; }
    public void setArchivoCorrecciones(String archivoCorrecciones) { this.archivoCorrecciones = archivoCorrecciones; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
    public Usuario getEvaluador() { return evaluador; }
    public void setEvaluador(Usuario evaluador) { this.evaluador = evaluador; }
    public AsignacionEvaluacion getAsignacion() { return asignacion; }
    public void setAsignacion(AsignacionEvaluacion asignacion) { this.asignacion = asignacion; }
}
