package model;

import java.util.HashSet;
import java.util.Set;

public class Permiso {
    private Long id;

    private String nombre;

    private String recurso;

    private String accion;

    private Set<Rol> roles = new HashSet<>();

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
    public Set<Rol> getRoles() { return roles; }
    public void setRoles(Set<Rol> roles) { this.roles = roles; }
}
