package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "etapas_congreso")
public class EtapaCongreso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fechaDesde;

    @Column(nullable = false)
    private LocalDate fechaHasta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "congreso_id")
    private Congreso congreso;

    public EtapaCongreso() {}
    public EtapaCongreso(String nombre, LocalDate fechaDesde, LocalDate fechaHasta) {
        this.nombre = nombre;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaDesde() { return fechaDesde; }
    public void setFechaDesde(LocalDate fechaDesde) { this.fechaDesde = fechaDesde; }
    public LocalDate getFechaHasta() { return fechaHasta; }
    public void setFechaHasta(LocalDate fechaHasta) { this.fechaHasta = fechaHasta; }
    public Congreso getCongreso() { return congreso; }
    public void setCongreso(Congreso congreso) { this.congreso = congreso; }
}
