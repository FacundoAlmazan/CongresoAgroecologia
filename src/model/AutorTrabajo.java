package model;


public class AutorTrabajo {
    private Long id;

    private Usuario autor;

    private Trabajo trabajo;

    private Integer orden;

    public AutorTrabajo() {}
    public AutorTrabajo(Usuario autor, Trabajo trabajo, Integer orden) {
        this.autor = autor;
        this.trabajo = trabajo;
        this.orden = orden;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
