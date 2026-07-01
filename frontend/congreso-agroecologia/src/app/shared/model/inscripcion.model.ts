import { DatosFactura } from "./datosFactura.model";
import { EstadoPago } from "./EstadoPago.enum.model";
import { Usuario } from "./usuario.model";


export interface Inscripcion {
  id?: number;

  usuario: Partial<Usuario>;

  monto: number;
  fechaPago?: string;

  comprobante?: string;
  comprobanteAcreditacion?: string;

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

  validadoPor?: Usuario;
}

