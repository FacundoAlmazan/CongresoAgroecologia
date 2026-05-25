package model;

import jakarta.persistence.*;

@Entity
@Table(name = "plantillas_email")
public class PlantillaEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String asunto;

    @Column(length = 5000, nullable = false)
    private String cuerpo;

    public PlantillaEmail() {}
    public PlantillaEmail(String asunto, String cuerpo) {
        this.asunto = asunto;
        this.cuerpo = cuerpo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getCuerpo() { return cuerpo; }
    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }
}
