package model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(example = "Charla de prueba")
    private String titulo;

    @Column(length = 3000)
    @Schema(example = "Actividad creada desde Swagger")
    private String descripcion;

    @Column(nullable = false)
    @Schema(example = "Aula 1")
    private String aula;

    @Column(nullable = false)
    @Schema(type = "string", format = "date", example = "2026-06-17")
    private LocalDate fecha;

    @Column(nullable = false)
    @Schema(type = "string", format = "time", example = "09:00:00")
    private LocalTime horaInicio;

    @Column(nullable = false)
    @Schema(type = "string", format = "time", example = "10:00:00")
    private LocalTime horaFin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cronograma_id")
    @JsonIgnoreProperties("actividades")
    @Schema(description = "Cronograma existente al que pertenece la actividad. En POST alcanza con enviar {\"id\": 1}.")
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
