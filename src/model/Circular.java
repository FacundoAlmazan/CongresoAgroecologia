package model;

import java.time.LocalDate;

public class Circular {
    private Long id;

    private String titulo;

    private String archivoPDF;

    private LocalDate fechaPublicacion;

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
