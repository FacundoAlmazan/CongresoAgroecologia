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
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para registrar pago/inscripción",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"usuario\":{\"id\":1},\"monto\":15000,\"fechaPago\":\"2026-06-17\",\"comprobante\":\"comprobante-transferencia.pdf\",\"efectivo\":false,\"requiereFactura\":true,\"datosFactura\":{\"razonSocial\":\"Universidad Nacional de La Plata\",\"cuit\":\"30-54666670-7\",\"condicionIva\":\"Exento\",\"domicilioFiscal\":\"Calle 50 y 120, La Plata\",\"emailFactura\":\"facturacion@unlp.edu.ar\"}}")
        )
    )
    public Response registrar(Inscripcion inscripcion) {
        Inscripcion creada = pagoService.registrar(inscripcion);
        return Response.created(URI.create("/pagos/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}/aprobar")
    @Operation(summary = "Aprobar pago")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Administrador que aprueba el pago",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"adminId\":1}")
        )
    )
    public Response aprobar(@PathParam("id") Long id, Map<String, Long> body) {
        return Response.ok(pagoService.aprobar(id, body.get("adminId"))).build();
    }

    @PUT
    @Path("/{id}/rechazar")
    @Operation(summary = "Rechazar asignacion o pago")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Administrador y motivo de rechazo",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"adminId\":\"1\",\"motivo\":\"Comprobante ilegible\"}")
        )
    )
    public Response rechazar(@PathParam("id") Long id, Map<String, String> body) {
        Long adminId = body.get("adminId") == null ? null : Long.valueOf(body.get("adminId"));
        return Response.ok(pagoService.rechazar(id, adminId, body.get("motivo"))).build();
    }
}
