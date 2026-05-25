package model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String asunto;

    @Column(length = 5000, nullable = false)
    private String mensaje;

    private LocalDate fechaEnvio;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "plantilla_id")
    private PlantillaEmail plantilla;

    public Notificacion() {}
    public Notificacion(String asunto, String mensaje, Usuario destinatario) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.fechaEnvio = LocalDate.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario destinatario) { this.destinatario = destinatario; }
    public PlantillaEmail getPlantilla() { return plantilla; }
    public void setPlantilla(PlantillaEmail plantilla) { this.plantilla = plantilla; }
}
