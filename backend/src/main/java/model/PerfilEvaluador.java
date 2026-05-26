package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfiles_evaluador")
public class PerfilEvaluador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private Boolean evaluadorEdicionesPasadas = false;

    @Column(nullable = false)
    private Boolean evaluadorOtrosCongresos = false;

    @Column(length = 1000)
    private String formacionAcademica;

    @Column(length = 1000)
    private String areasConocimiento;

    @Column(length = 1000)
    private String subareasConocimiento;

    @Column(length = 2000)
    private String comentario;

    @OneToMany(mappedBy = "perfilEvaluador")
    private Set<DisponibilidadEvaluadorEje> disponibilidades = new HashSet<>();

    public PerfilEvaluador() {
    }

    public PerfilEvaluador(Usuario usuario) {
        this.usuario = usuario;
    }

    public void agregarDisponibilidad(DisponibilidadEvaluadorEje disponibilidad) {
        this.disponibilidades.add(disponibilidad);
        disponibilidad.setPerfilEvaluador(this);
    }

    public void quitarDisponibilidad(DisponibilidadEvaluadorEje disponibilidad) {
        this.disponibilidades.remove(disponibilidad);
        disponibilidad.setPerfilEvaluador(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getEvaluadorEdicionesPasadas() {
        return evaluadorEdicionesPasadas;
    }

    public void setEvaluadorEdicionesPasadas(Boolean evaluadorEdicionesPasadas) {
        this.evaluadorEdicionesPasadas = evaluadorEdicionesPasadas;
    }

    public Boolean getEvaluadorOtrosCongresos() {
        return evaluadorOtrosCongresos;
    }

    public void setEvaluadorOtrosCongresos(Boolean evaluadorOtrosCongresos) {
        this.evaluadorOtrosCongresos = evaluadorOtrosCongresos;
    }

    public String getFormacionAcademica() {
        return formacionAcademica;
    }

    public void setFormacionAcademica(String formacionAcademica) {
        this.formacionAcademica = formacionAcademica;
    }

    public String getAreasConocimiento() {
        return areasConocimiento;
    }

    public void setAreasConocimiento(String areasConocimiento) {
        this.areasConocimiento = areasConocimiento;
    }

    public String getSubareasConocimiento() {
        return subareasConocimiento;
    }

    public void setSubareasConocimiento(String subareasConocimiento) {
        this.subareasConocimiento = subareasConocimiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Set<DisponibilidadEvaluadorEje> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(Set<DisponibilidadEvaluadorEje> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }
}
