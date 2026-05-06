package model;

import java.util.ArrayList;
import java.util.List;

public class Cronograma {
    private Long id;

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
