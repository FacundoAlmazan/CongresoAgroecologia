package model.dto;

import java.util.Set;
import java.util.stream.Collectors;
import model.Usuario;

public class UsuarioAutenticadoResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String numeroIdentificacion;
    private String nacionalidad;
    private String institucion;
    private Boolean activo;
    private Set<String> roles;

    public UsuarioAutenticadoResponse() {}

    public static UsuarioAutenticadoResponse desde(Usuario usuario) {
        UsuarioAutenticadoResponse response = new UsuarioAutenticadoResponse();
        response.id = usuario.getId();
        response.nombre = usuario.getNombre();
        response.apellido = usuario.getApellido();
        response.email = usuario.getEmail();
        response.telefono = usuario.getTelefono();
        response.numeroIdentificacion = usuario.getNumeroIdentificacion();
        response.nacionalidad = usuario.getNacionalidad();
        response.institucion = usuario.getInstitucion();
        response.activo = usuario.getActivo();
        response.roles = usuario.getRoles().stream()
                .map(rol -> rol.getNombre().name())
                .collect(Collectors.toSet());
        return response;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public String getNacionalidad() { return nacionalidad; }
    public String getInstitucion() { return institucion; }
    public Boolean getActivo() { return activo; }
    public Set<String> getRoles() { return roles; }
}
