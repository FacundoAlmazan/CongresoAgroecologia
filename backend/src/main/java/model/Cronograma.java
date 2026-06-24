package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "cronogramas")
public class Cronograma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @OneToMany(mappedBy = "cronograma")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private List<Actividad> actividades = new ArrayList<>();

    public Cronograma() {}

    public void agregarActividad(Actividad actividad) {
        actividades.add(actividad);
        actividad.setCronograma(this);
    }

    public boolean tieneConflicto(Actividad nuevaActividad) {
        return actividades.stream().anyMatch(a -> a.seSuperponeCon(nuevaActividad));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public List<Actividad> getActividades() { return actividades; }
    public void setActividades(List<Actividad> actividades) { this.actividades = actividades; }
}
