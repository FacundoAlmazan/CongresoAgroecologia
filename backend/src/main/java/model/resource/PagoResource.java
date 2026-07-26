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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.QueryParam;
import jakarta.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.Map;
import model.Inscripcion;
import model.dto.InscripcionResumenResponse;
import model.service.PagoService;
import model.enums.EstadoPago;
import model.security.Secured;

@Tag(name = "Pago", description = "Gestion de pagos e inscripciones")
@Path("/pagos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagoResource {

    @Inject
    private PagoService pagoService;

    @GET
    @Secured
    @RolesAllowed("ADMIN")
    @Operation(summary = "Listar recursos")
    public Response listar(@QueryParam("estado") EstadoPago estado) {
        return Response.ok(pagoService.listar(estado).stream()
                .map(InscripcionResumenResponse::desde)
                .toList()).build();
    }

    @GET
    @Path("/mio")
    @Secured
    public Response consultarMio(@Context SecurityContext securityContext) {
        Inscripcion inscripcion = pagoService.obtenerMia(usuarioId(securityContext));
        return inscripcion == null ? Response.noContent().build() : Response.ok(inscripcion).build();
    }

    @GET
    @Path("/{id}")
    @Secured
    @RolesAllowed("ADMIN")
    @Operation(summary = "Consultar estado de pago")
    public Response consultarEstado(@PathParam("id") Long id) { return Response.ok(pagoService.obtener(id)).build(); }

    @POST
    @Secured
    @Operation(summary = "registrar")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Datos para registrar pago/inscripción",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"usuario\":{\"id\":1},\"monto\":15000,\"fechaPago\":\"2026-06-17\",\"comprobante\":\"comprobante-transferencia.pdf\",\"efectivo\":false,\"requiereFactura\":true,\"datosFactura\":{\"razonSocial\":\"Universidad Nacional de La Plata\",\"cuit\":\"30-54666670-7\",\"condicionIva\":\"Exento\",\"domicilioFiscal\":\"Calle 50 y 120, La Plata\",\"emailFactura\":\"facturacion@unlp.edu.ar\"}}")
        )
    )
    public Response registrar(Inscripcion inscripcion, @Context SecurityContext securityContext) {
        Inscripcion creada = pagoService.registrar(inscripcion, usuarioId(securityContext));
        return Response.created(URI.create("/pagos/" + creada.getId())).entity(creada).build();
    }

    @PUT
    @Path("/{id}/aprobar")
    @Secured
    @RolesAllowed("ADMIN")
    @Operation(summary = "Aprobar pago")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Administrador que aprueba el pago",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"adminId\":1}")
        )
    )
    public Response aprobar(
            @PathParam("id") Long id,
            Map<String, Object> body,
            @Context SecurityContext securityContext) {
        Object monto = body.get("montoAbonado");
        Double montoAbonado = monto instanceof Number ? ((Number) monto).doubleValue() : null;
        return Response.ok(pagoService.aprobar(
                id, usuarioId(securityContext), montoAbonado)).build();
    }

    @PUT
    @Path("/{id}/observar")
    @Secured
    @RolesAllowed("ADMIN")
    public Response observar(
            @PathParam("id") Long id,
            Map<String, Object> body,
            @Context SecurityContext securityContext) {
        Object monto = body.get("montoAbonado");
        Double montoAbonado = monto instanceof Number ? ((Number) monto).doubleValue() : null;
        return Response.ok(pagoService.observar(
                id,
                usuarioId(securityContext),
                String.valueOf(body.getOrDefault("comentario", "")),
                montoAbonado)).build();
    }

    @PUT
    @Path("/{id}/rechazar")
    @Secured
    @RolesAllowed("ADMIN")
    @Operation(summary = "Rechazar asignacion o pago")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "Administrador y motivo de rechazo",
        content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = MediaType.APPLICATION_JSON,
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(value = "{\"adminId\":\"1\",\"motivo\":\"Comprobante ilegible\"}")
        )
    )
    public Response rechazar(
            @PathParam("id") Long id,
            Map<String, String> body,
            @Context SecurityContext securityContext) {
        return Response.ok(pagoService.rechazar(
                id, usuarioId(securityContext), body.get("motivo"))).build();
    }

    @PUT
    @Path("/mio/reenviar")
    @Secured
    public Response reenviar(Inscripcion datos, @Context SecurityContext securityContext) {
        return Response.ok(pagoService.reenviar(datos, usuarioId(securityContext))).build();
    }

    private Long usuarioId(SecurityContext securityContext) {
        return Long.valueOf(securityContext.getUserPrincipal().getName());
    }
}
