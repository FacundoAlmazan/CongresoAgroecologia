package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Congreso {
    private Long id;

    private String nombre;

    private String edicion;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private Cronograma cronograma;

    private List<EtapaCongreso> etapas = new ArrayList<>();

    public Congreso() {}
    public Congreso(String nombre, String edicion) {
        this.nombre = nombre;
        this.edicion = edicion;
    }

    public void agregarEtapa(EtapaCongreso etapa) {
        etapas.add(etapa);
        etapa.setCongreso(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEdicion() { return edicion; }
    public void setEdicion(String edicion) { this.edicion = edicion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Cronograma getCronograma() { return cronograma; }
    public void setCronograma(Cronograma cronograma) { this.cronograma = cronograma; }
    public List<EtapaCongreso> getEtapas() { return etapas; }
    public void setEtapas(List<EtapaCongreso> etapas) { this.etapas = etapas; }
}
