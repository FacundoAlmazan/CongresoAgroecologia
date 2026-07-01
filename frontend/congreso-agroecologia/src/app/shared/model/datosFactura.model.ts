import { Inscripcion } from './inscripcion.model';

export interface DatosFactura {
  id?: number;

  razonSocial: string;
  cuit: string;
  condicionIva: string;
  domicilioFiscal: string;
  emailFactura: string;

  otrosDatos?: string;

  inscripcion?: Inscripcion;
}