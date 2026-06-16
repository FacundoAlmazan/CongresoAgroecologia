package model;

import jakarta.persistence.*;
import model.enums.EstadoTrabajo;
import model.enums.ModalidadPresentacion;
import model.enums.TipoTrabajo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(nullable = false)
    private String passwordTrabajo;

    private String telefonoContacto;

    @Enumerated(EnumType.STRING)
    private ModalidadPresentacion modalidadSolicitada;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTrabajo tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTrabajo estado = EstadoTrabajo.BORRADOR;

    @ManyToOne
    @JoinColumn(name = "autor_contacto_id")
    private Usuario autorContacto;

    @OneToMany(mappedBy = "trabajo")
    @OrderBy("ordenAutor ASC")
    private List<AutorTrabajo> autores = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "trabajos_ejes_tematicos",
        joinColumns = @JoinColumn(name = "trabajo_id"),
        inverseJoinColumns = @JoinColumn(name = "eje_tematico_id")
    )
    private Set<EjeTematico> ejesTematicos = new HashSet<>();

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
        this.passwordTrabajo = "pendiente";
    }

    public Trabajo(String titulo, String resumen, TipoTrabajo tipo, String passwordTrabajo) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.tipo = tipo;
        this.passwordTrabajo = passwordTrabajo;
    }

    public void agregarAutor(AutorTrabajo autor) {
        autores.add(autor);
        autor.setTrabajo(this);
    }

    public void quitarAutor(AutorTrabajo autor) {
        autores.remove(autor);
        autor.setTrabajo(null);
    }

    public void agregarEjeTematico(EjeTematico ejeTematico) {
        ejesTematicos.add(ejeTematico);
        ejeTematico.getTrabajos().add(this);
    }

    public void quitarEjeTematico(EjeTematico ejeTematico) {
        ejesTematicos.remove(ejeTematico);
        ejeTematico.getTrabajos().remove(this);
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
    public String getPasswordTrabajo() { return passwordTrabajo; }
    public void setPasswordTrabajo(String passwordTrabajo) { this.passwordTrabajo = passwordTrabajo; }
    public String getTelefonoContacto() { return telefonoContacto; }
    public void setTelefonoContacto(String telefonoContacto) { this.telefonoContacto = telefonoContacto; }
    public ModalidadPresentacion getModalidadSolicitada() { return modalidadSolicitada; }
    public void setModalidadSolicitada(ModalidadPresentacion modalidadSolicitada) { this.modalidadSolicitada = modalidadSolicitada; }
    public TipoTrabajo getTipo() { return tipo; }
    public void setTipo(TipoTrabajo tipo) { this.tipo = tipo; }
    public EstadoTrabajo getEstado() { return estado; }
    public void setEstado(EstadoTrabajo estado) { this.estado = estado; }
    public Usuario getAutorContacto() { return autorContacto; }
    public void setAutorContacto(Usuario autorContacto) { this.autorContacto = autorContacto; }
    public List<AutorTrabajo> getAutores() { return autores; }
    public void setAutores(List<AutorTrabajo> autores) { this.autores = autores; }
    public Set<EjeTematico> getEjesTematicos() { return ejesTematicos; }
    public void setEjesTematicos(Set<EjeTematico> ejesTematicos) { this.ejesTematicos = ejesTematicos; }
    public List<AsignacionEvaluacion> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionEvaluacion> asignaciones) { this.asignaciones = asignaciones; }
    public List<Evaluacion> getEvaluaciones() { return evaluaciones; }
    public void setEvaluaciones(List<Evaluacion> evaluaciones) { this.evaluaciones = evaluaciones; }
    public DecisionTrabajo getDecisionTrabajo() { return decisionTrabajo; }
    public void setDecisionTrabajo(DecisionTrabajo decisionTrabajo) { this.decisionTrabajo = decisionTrabajo; }
}
