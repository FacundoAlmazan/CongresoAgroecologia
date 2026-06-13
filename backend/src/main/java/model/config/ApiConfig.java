package model.config;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ApiConfig extends ResourceConfig {

    public ApiConfig() {
        packages("model.resource");
    }
}
