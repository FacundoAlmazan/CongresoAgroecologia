export enum EstadoPago {
  PENDIENTE = 'PENDIENTE',
  // Agregar el resto de los estados del enum de Java, por ejemplo: APROBADO, RECHAZADO
}
/* 
export interface Inscripcion {
  id?: number; // Opcional por ser autogenerado en el backend
  usuario: Usuario; // Obligatorio debido a optional = false en el ManyToOne
  monto: number;
  fechaPago?: string; // Formato string ISO (YYYY-MM-DD) para persistencia en JSON
  comprobante?: string;
  comprobanteAcreditacion?: string;
  tipoInscripcion?: string;
  filiacionInstitucional?: string;
  formasParticipacion?: string;
  requiereFactura: boolean;
  datosFactura?: DatosFactura; // Relación OneToOne inversa, puede ser indefinida
  requiereAdecuacion: boolean;
  detalleAdecuacion?: string;
  tieneAlergiaOIntolerancia: boolean;
  detalleAlergiaOIntolerancia?: string;
  observaciones?: string;
  efectivo: boolean;
  estado: EstadoPago;
  motivoRechazo?: string;
  validadoPor?: Usuario; // Relación ManyToOne opcional
  qr?: QR; // Relación OneToOne inversa, puede ser indefinida
}*/