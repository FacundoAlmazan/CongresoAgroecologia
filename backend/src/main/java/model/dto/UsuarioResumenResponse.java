package model.dto;

import model.Usuario;

public class UsuarioResumenResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String numeroIdentificacion;

    public UsuarioResumenResponse() {}

    public static UsuarioResumenResponse desde(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResumenResponse response = new UsuarioResumenResponse();
        response.id = usuario.getId();
        response.nombre = usuario.getNombre();
        response.apellido = usuario.getApellido();
        response.email = usuario.getEmail();
        response.numeroIdentificacion = usuario.getNumeroIdentificacion();
        return response;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
}
