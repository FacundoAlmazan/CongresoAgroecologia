package model.security;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import java.security.Principal;
import java.util.List;
import model.service.JwtService;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class JwtAuthenticationFilter implements ContainerRequestFilter {
    @Inject
    private JwtService jwtService;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            abortar(requestContext, "Falta el token de autenticación");
            return;
        }

        try {
            Claims claims = jwtService.validarYObtenerClaims(authorization.substring(7));
            String usuarioId = claims.getSubject();
            List<?> roles = claims.get("roles", List.class);
            SecurityContext anterior = requestContext.getSecurityContext();

            requestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> usuarioId;
                }

                @Override
                public boolean isUserInRole(String role) {
                    return roles != null && roles.stream().anyMatch(r -> role.equals(String.valueOf(r)));
                }

                @Override
                public boolean isSecure() {
                    return anterior != null && anterior.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return "Bearer";
                }
            });
        } catch (Exception e) {
            abortar(requestContext, "Token inválido o vencido");
        }
    }

    private void abortar(ContainerRequestContext context, String mensaje) {
        context.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorResponse(mensaje))
                .build());
    }

    public record ErrorResponse(String message) {}
}
