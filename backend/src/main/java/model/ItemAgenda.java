package model;

import jakarta.persistence.*;

@Entity
@Table(name = "items_agenda")
public class ItemAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @ManyToOne(optional = false)
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;

    public ItemAgenda() {}
    public ItemAgenda(Agenda agenda, Actividad actividad) {
        this.agenda = agenda;
        this.actividad = actividad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Agenda getAgenda() { return agenda; }
    public void setAgenda(Agenda agenda) { this.agenda = agenda; }
    public Actividad getActividad() { return actividad; }
    public void setActividad(Actividad actividad) { this.actividad = actividad; }
}
