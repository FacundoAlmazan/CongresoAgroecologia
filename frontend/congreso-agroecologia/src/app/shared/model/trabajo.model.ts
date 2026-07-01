import { AsignacionEvaluacion } from "./asignacionEvaluacion.model";
import { AutorTrabajo } from "./autorTrabajo.model";
import { DecisionTrabajo } from "./decisionTrabajo.model";
import { EjeTematico } from "./ejeTematico.model";
import { EstadoTrabajo } from "./EstadoTrabajo.enum.model";
import { Evaluacion } from "./evaluacion.model";
import { ModalidadPresentacion } from "./ModalidadPresentacion.enum.model";
import { TipoTrabajo } from "./TipoTrabajo.enum.model";
import { Usuario } from "./usuario.model";



export interface Trabajo {
  id?: number; // Opcional si se genera en el backend al crear el registro
  titulo: string;
  resumen?: string;
  tipo: TipoTrabajo;
  modalidadSolicitada?: ModalidadPresentacion;
  ejesTematicos: EjeTematico[];
  passwordTrabajo?: string;
  archivo?: string;
  fechaEnvio?: string; // Formato string ISO (YYYY-MM-DD) recomendado para la serialización
  telefonoContacto?: string;
  estado?: EstadoTrabajo;
  autorContacto?: Usuario;
  autores: AutorTrabajo[];
  asignaciones?: AsignacionEvaluacion[];
  evaluaciones?: Evaluacion[];
  decisionTrabajo?: DecisionTrabajo;
} 