package model.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;
import model.Inscripcion;
import model.service.PagoService;

@Tag(name = "Pago", description = "Gestion de pagos e inscripciones")
@Path("/pagos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoResource {

    @Inject
    private PagoService pagoService;

    @GET
    @Operation(summary = "Listar recursos")
    public Response listar() { return Response.ok(pagoService.listar()).build(); }

    @GET
    @Path("/{id}")
    @Operation(summary = "Consultar estado de pago")
    public Response consultarEstado(@PathParam("id") Long id) { return Response.ok(pagoService.obtener(id)).build(); }

    @POST
    @Operation(summary = "registrar")
    public Response registrar(Inscripcion inscripcion) {
        Inscripcion creada = pagoService.registrar(inscripcion);
        return Response.created(URI.create("/pagos/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}/aprobar")
    @Operation(summary = "Aprobar pago")
    public Response aprobar(@PathParam("id") Long id, Map<String, Long> body) {
        return Response.ok(pagoService.aprobar(id, body.get("adminId"))).build();
    }

    @PUT
    @Path("/{id}/rechazar")
    @Operation(summary = "Rechazar asignacion o pago")
    public Response rechazar(@PathParam("id") Long id, Map<String, String> body) {
        Long adminId = body.get("adminId") == null ? null : Long.valueOf(body.get("adminId"));
        return Response.ok(pagoService.rechazar(id, adminId, body.get("motivo"))).build();
    }
}
