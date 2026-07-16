package model.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class PasswordService {

    private static final int COSTO = 12;

    public String hashear(String passwordPlano) {
        if (passwordPlano == null || passwordPlano.isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }

        return BCrypt.hashpw(
                passwordPlano,
                BCrypt.gensalt(COSTO)
        );
    }

    public boolean coincide(
            String passwordPlano,
            String passwordGuardado
    ) {
        if (passwordPlano == null || passwordGuardado == null) {
            return false;
        }

        if (estaHasheado(passwordGuardado)) {
            return BCrypt.checkpw(
                    passwordPlano,
                    passwordGuardado
            );
        }

        // Compatibilidad temporal con usuarios viejos.
        return passwordPlano.equals(passwordGuardado);
    }

    public boolean estaHasheado(String password) {
        return password != null
                && (
                    password.startsWith("$2a$")
                    || password.startsWith("$2b$")
                    || password.startsWith("$2y$")
                );
    }
}
