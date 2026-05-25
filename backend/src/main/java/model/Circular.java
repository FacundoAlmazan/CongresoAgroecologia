package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "circulares")
public class Circular {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String archivoPDF;

    private LocalDate fechaPublicacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "publicado_por_id")
    private Usuario publicadoPor;

    public Circular() {}
    public Circular(String titulo, String archivoPDF, Usuario publicadoPor) {
        this.titulo = titulo;
        this.archivoPDF = archivoPDF;
        this.publicadoPor = publicadoPor;
        this.fechaPublicacion = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getArchivoPDF() { return archivoPDF; }
    public void setArchivoPDF(String archivoPDF) { this.archivoPDF = archivoPDF; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public Usuario getPublicadoPor() { return publicadoPor; }
    public void setPublicadoPor(Usuario publicadoPor) { this.publicadoPor = publicadoPor; }
}
