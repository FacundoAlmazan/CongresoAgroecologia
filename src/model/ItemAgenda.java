package model;


public class ItemAgenda {
    private Long id;

    private Agenda agenda;

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
