package model;

import java.time.LocalDate;

public class QR {
    private Long id;

    private String codigo;

    private LocalDate fechaGeneracion;

    private Boolean utilizado = false;

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
    public Boolean getUtilizado() { return utilizado; }
    public void setUtilizado(Boolean utilizado) { this.utilizado = utilizado; }
    public Inscripcion getInscripcion() { return inscripcion; }
    public void setInscripcion(Inscripcion inscripcion) { this.inscripcion = inscripcion; }
}
