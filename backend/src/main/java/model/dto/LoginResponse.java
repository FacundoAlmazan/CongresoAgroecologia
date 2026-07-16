package model.dto;

public class LoginResponse {
    private String token;
    private UsuarioAutenticadoResponse usuario;

    public LoginResponse() {}

    public LoginResponse(String token, UsuarioAutenticadoResponse usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public UsuarioAutenticadoResponse getUsuario() { return usuario; }
}
