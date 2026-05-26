package model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores_trabajo")
public class AutorTrabajo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private String organizacion;
    private String pais;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer ordenAutor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trabajo_id")
    private Trabajo trabajo;

    public AutorTrabajo() {}

    public AutorTrabajo(String nombre, String apellido, String organizacion, String pais, String email, Integer ordenAutor) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.organizacion = organizacion;
        this.pais = pais;
        this.email = email;
        this.ordenAutor = ordenAutor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getOrganizacion() { return organizacion; }
    public void setOrganizacion(String organizacion) { this.organizacion = organizacion; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getOrdenAutor() { return ordenAutor; }
    public void setOrdenAutor(Integer ordenAutor) { this.ordenAutor = ordenAutor; }
    public Trabajo getTrabajo() { return trabajo; }
    public void setTrabajo(Trabajo trabajo) { this.trabajo = trabajo; }
}
