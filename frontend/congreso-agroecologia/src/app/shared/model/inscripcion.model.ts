import { DatosFactura } from "./datosFactura.model";
import { EstadoPago } from "./EstadoPago.enum.model";
import { Usuario } from "./usuario.model";


export interface Inscripcion {
  id?: number; 
  usuario: Usuario; // Obligatorio debido a optional = false en el ManyToOne
  monto: number;
  fechaPago?: string; // Formato string ISO (YYYY-MM-DD) para persistencia en JSON
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
  //qr?: QR; // Relación OneToOne inversa, puede ser indefinida
}