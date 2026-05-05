package model;

import model.enums.TipoRol;
import java.util.HashSet;
import java.util.Set;

public class Rol {
    private Long id;

    private TipoRol nombre;

    private Set<Usuario> usuarios = new HashSet<>();

    public Rol() {}
    public Rol(TipoRol nombre) { this.nombre = nombre; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TipoRol getNombre() { return nombre; }
    public void setNombre(TipoRol nombre) { this.nombre = nombre; }
    public Set<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Set<Usuario> usuarios) { this.usuarios = usuarios; }
}
