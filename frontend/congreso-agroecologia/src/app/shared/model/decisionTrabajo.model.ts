
import { EstadoTrabajo } from './EstadoTrabajo.enum.model';
import { Trabajo } from './trabajo.model';
import { Usuario } from './usuario.model';

export interface DecisionTrabajo {
  id?: number;
  fecha?: string;
  decision: EstadoTrabajo;
  comentario?: string;
  trabajo: Trabajo;
  organizadorCientifico: Usuario;
}