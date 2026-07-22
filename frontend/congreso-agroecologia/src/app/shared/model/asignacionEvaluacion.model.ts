import { EstadoAsignacion } from './EstadoAsignacion.enum.model';
import { Trabajo } from './trabajo.model';
import { Usuario } from './usuario.model';


export interface AsignacionEvaluacion {
  id?: number;
  trabajo: Trabajo;
  finalizado?: boolean;
  evaluador: Usuario;
  asignadoPor: Usuario;
  fechaAsignacion?: string;
  estado: EstadoAsignacion;
}