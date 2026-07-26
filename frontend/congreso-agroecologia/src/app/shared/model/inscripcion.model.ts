import { DatosFactura } from "./datosFactura.model";
import { EstadoPago } from "./EstadoPago.enum.model";
import { Usuario } from "./usuario.model";

export type FechaApi = string | number[];

export interface Inscripcion {
  id?: number;

  usuario: Partial<Usuario>;

  monto: number;
  montoAbonado?: number;
  diferenciaMonto?: number;
  fechaPago?: FechaApi;
  fechaRegistro?: FechaApi;

  comprobante?: string;
  comprobanteContenido?: string;
  comprobanteAcreditacion?: string;
  comprobanteAcreditacionContenido?: string;

  tipoInscripcion?: string;
  filiacionInstitucional?: string;
  formasParticipacion?: string;

  requiereFactura: boolean;
  datosFactura?: DatosFactura;

  requiereAdecuacion: boolean;
  detalleAdecuacion?: string;

  tieneAlergiaOIntolerancia: boolean;
  detalleAlergiaOIntolerancia?: string;

  observaciones?: string;

  efectivo: boolean;

  estado: EstadoPago;

  motivoRechazo?: string;
  comentarioRevision?: string;

  validadoPor?: Usuario;
  qr?: {
    id?: number;
    codigo: string;
    fechaGeneracion?: FechaApi;
    utilizado?: boolean;
    imagen?: string;
  };
}
