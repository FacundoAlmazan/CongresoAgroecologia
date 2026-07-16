package model.service;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import model.Usuario;
import model.dto.LoginRequest;
import model.dto.LoginResponse;
import model.dto.UsuarioAutenticadoResponse;
import model.interfaceDao.IUsuarioDAO;

public class AuthService {
    @Inject
    private IUsuarioDAO usuarioDAO;

    @Inject
    private JwtService jwtService;

    @Inject
    private PasswordService passwordService;

    public LoginResponse login(LoginRequest request) {
        if (request == null || request.getNumeroIdentificacion() == null || request.getPassword() == null) {
            throw new BadRequestException("DNI y contraseña son obligatorios");
        }

        Usuario usuario = usuarioDAO.getByNumeroIdentificacionConRoles(
                request.getNumeroIdentificacion().trim()
        );

        if (usuario == null || !passwordService.coincide(request.getPassword(), usuario.getPassword())) {
            throw new NotAuthorizedException("DNI o contraseña incorrectos");
        }

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new NotAuthorizedException("La cuenta está desactivada");
        }

        // Migra automáticamente las contraseñas antiguas en texto plano.
        if (!passwordService.estaHasheado(usuario.getPassword())) {
            usuario.setPassword(passwordService.hashear(request.getPassword()));
            usuarioDAO.modificar(usuario);
        }

        String token = jwtService.generarToken(usuario);
        return new LoginResponse(token, UsuarioAutenticadoResponse.desde(usuario));
    }
}
