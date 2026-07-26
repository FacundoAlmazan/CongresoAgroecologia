package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "qrs")
public class QR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    private LocalDate fechaGeneracion;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String imagen;

    @Column(nullable = false)
    private Boolean utilizado = false;

    @OneToOne(optional = false)
    @JoinColumn(name = "inscripcion_id", unique = true)
    private Inscripcion inscripcion;

    public QR() {}
    public QR(String codigo, Inscripcion inscripcion) {
        this.codigo = codigo;
        this.inscripcion = inscripcion;
        this.fechaGeneracion = LocalDate.now();
    }

    public void marcarComoUtilizado() { this.utilizado = true; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public LocalDate getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDate fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public Boolean getUtilizado() { return utilizado; }
    public void setUtilizado(Boolean utilizado) { this.utilizado = utilizado; }
    public Inscripcion getInscripcion() { return inscripcion; }
    public void setInscripcion(Inscripcion inscripcion) { this.inscripcion = inscripcion; }
}
