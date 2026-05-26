package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ejes_tematicos")
public class EjeTematico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @ManyToMany(mappedBy = "ejesTematicos")
    private Set<Trabajo> trabajos = new HashSet<>();

    public EjeTematico() {}

    public EjeTematico(String nombre) {
        this.nombre = nombre;
    }

    public EjeTematico(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Set<Trabajo> getTrabajos() { return trabajos; }
    public void setTrabajos(Set<Trabajo> trabajos) { this.trabajos = trabajos; }
}
