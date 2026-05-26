package model;

import jakarta.persistence.*;

@Entity
@Table(name = "disponibilidades_evaluador_eje", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"perfil_evaluador_id", "eje_tematico_id"})
})
public class DisponibilidadEvaluadorEje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "perfil_evaluador_id")
    private PerfilEvaluador perfilEvaluador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "eje_tematico_id")
    private EjeTematico ejeTematico;

    @Column(nullable = false)
    private Integer cantidadTrabajos;

    public DisponibilidadEvaluadorEje() {}

    public DisponibilidadEvaluadorEje(PerfilEvaluador perfilEvaluador, EjeTematico ejeTematico, Integer cantidadTrabajos) {
        this.perfilEvaluador = perfilEvaluador;
        this.ejeTematico = ejeTematico;
        this.cantidadTrabajos = cantidadTrabajos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PerfilEvaluador getPerfilEvaluador() { return perfilEvaluador; }
    public void setPerfilEvaluador(PerfilEvaluador perfilEvaluador) { this.perfilEvaluador = perfilEvaluador; }
    public EjeTematico getEjeTematico() { return ejeTematico; }
    public void setEjeTematico(EjeTematico ejeTematico) { this.ejeTematico = ejeTematico; }
    public Integer getCantidadTrabajos() { return cantidadTrabajos; }
    public void setCantidadTrabajos(Integer cantidadTrabajos) { this.cantidadTrabajos = cantidadTrabajos; }
}
