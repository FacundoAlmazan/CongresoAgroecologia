package model.resource;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof NotFoundException) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof BadRequestException) {
            status = Response.Status.BAD_REQUEST;
        } else if (exception instanceof WebApplicationException) {
            Response response = ((WebApplicationException) exception).getResponse();
            status = Response.Status.fromStatusCode(response.getStatus());
            if (status == null) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
            }
        }

        Map<String, String> error = new HashMap<>();
        error.put("error", exception.getMessage() == null ? status.getReasonPhrase() : exception.getMessage());
        return Response.status(status).type(MediaType.APPLICATION_JSON).entity(error).build();
    }
}
