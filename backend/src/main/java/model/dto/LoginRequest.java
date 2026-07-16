package model.dto;

public class LoginRequest {
    private String numeroIdentificacion;
    private String password;

    public LoginRequest() {}

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
