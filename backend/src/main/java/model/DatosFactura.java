package model;

import jakarta.persistence.*;


@Entity
@Table(name = "datos_factura")
public class DatosFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String razonSocial;

    @Column(nullable = false)
    private String cuit;

    @Column(nullable = false)
    private String condicionIva;

    @Column(nullable = false)
    private String domicilioFiscal;

    @Column(nullable = false)
    private String emailFactura;

    @Column(length = 1000)
    private String otrosDatos;

    @OneToOne(optional = false)
    @JoinColumn(name = "inscripcion_id", unique = true)
    private Inscripcion inscripcion;

    public DatosFactura() {}

    public DatosFactura(String razonSocial, String cuit, String condicionIva, String domicilioFiscal, String emailFactura) {
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.condicionIva = condicionIva;
        this.domicilioFiscal = domicilioFiscal;
        this.emailFactura = emailFactura;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getCondicionIva() { return condicionIva; }
    public void setCondicionIva(String condicionIva) { this.condicionIva = condicionIva; }
    public String getDomicilioFiscal() { return domicilioFiscal; }
    public void setDomicilioFiscal(String domicilioFiscal) { this.domicilioFiscal = domicilioFiscal; }
    public String getEmailFactura() { return emailFactura; }
    public void setEmailFactura(String emailFactura) { this.emailFactura = emailFactura; }
    public String getOtrosDatos() { return otrosDatos; }
    public void setOtrosDatos(String otrosDatos) { this.otrosDatos = otrosDatos; }
    public Inscripcion getInscripcion() { return inscripcion; }
    public void setInscripcion(Inscripcion inscripcion) { this.inscripcion = inscripcion; }
}
