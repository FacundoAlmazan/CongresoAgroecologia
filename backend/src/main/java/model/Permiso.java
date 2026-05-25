package model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permisos", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"recurso", "accion"})
})
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String recurso;

    @Column(nullable = false)
    private String accion;

    @ManyToMany(mappedBy = "permisos")
    private Set<Usuario> usuarios = new HashSet<>();

    public Permiso() {}
    public Permiso(String nombre, String recurso, String accion) {
        this.nombre = nombre;
        this.recurso = recurso;
        this.accion = accion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }
    public String getAccion() { return accion; }
    public void setAccion(String accion) { this.accion = accion; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }
}
