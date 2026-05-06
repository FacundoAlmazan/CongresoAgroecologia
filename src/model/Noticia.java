package model;

import java.time.LocalDate;

public class Noticia {
    private Long id;

    private String titulo;

    private String contenido;

    private LocalDate fechaPublicacion;

    private Usuario publicadoPor;

    public Noticia() {}
    public Noticia(String titulo, String contenido, Usuario publicadoPor) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.publicadoPor = publicadoPor;
        this.fechaPublicacion = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public Usuario getPublicadoPor() { return publicadoPor; }
    public void setPublicadoPor(Usuario publicadoPor) { this.publicadoPor = publicadoPor; }
}
