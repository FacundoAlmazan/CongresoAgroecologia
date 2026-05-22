package model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private Long id;

    private Usuario usuario;

    private List<ItemAgenda> items = new ArrayList<>();

    public Agenda() {}
    public Agenda(Usuario usuario) { this.usuario = usuario; }

    public boolean tieneConflicto(Actividad actividad) {
        return items.stream().map(ItemAgenda::getActividad).anyMatch(a -> a.seSuperponeCon(actividad));
    }

    public void agregarActividad(Actividad actividad) {
        if (tieneConflicto(actividad)) {
            throw new IllegalArgumentException("La actividad se superpone con otra actividad de la agenda");
        }
        ItemAgenda item = new ItemAgenda(this, actividad);
        items.add(item);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<ItemAgenda> getItems() { return items; }
    public void setItems(List<ItemAgenda> items) { this.items = items; }
}
