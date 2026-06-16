package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(length = 3000)
    private String descripcion;

    @Column(nullable = false)
    private String aula;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cronograma_id")
    @JsonIgnore
    private Cronograma cronograma;

    public Actividad() {}
    public Actividad(String titulo, String aula, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.titulo = titulo;
        this.aula = aula;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public boolean seSuperponeCon(Actividad otra) {
        return this.fecha.equals(otra.fecha)
            && this.aula.equalsIgnoreCase(otra.aula)
            && this.horaInicio.isBefore(otra.horaFin)
            && this.horaFin.isAfter(otra.horaInicio);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getAula() { return aula; }
    public void setAula(String aula) { this.aula = aula; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Cronograma getCronograma() { return cronograma; }
    public void setCronograma(Cronograma cronograma) { this.cronograma = cronograma; }
}
