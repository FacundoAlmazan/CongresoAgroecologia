
import { Trabajo } from './trabajo.model';
import { Usuario } from './usuario.model';

import { ModalidadPresentacion } from './ModalidadPresentacion.enum.model';
import { Recomendacion } from './Recomendacion.enum.model';
import { TipoTrabajo } from './TipoTrabajo.enum.model';
import { AsignacionEvaluacion } from './asignacionEvaluacion.model';


export interface Evaluacion {
  id?: number;
  fecha?: string;
  recomendacion: Recomendacion;
  comentarios?: string;
  pertinenteCongreso: boolean;
  identificoAutores: boolean;
  conflictoIntereses: boolean;
  ejeTematicoCorrecto: boolean;
  lenguajeAdecuado?: boolean;
  tituloAdecuado?: boolean;
  resumenAdecuado?: boolean;
  palabrasClaveAdecuadas?: boolean;
  bibliografiaApa?: boolean;
  bibliografiaCompleta?: boolean;
  introduccionAdecuada?: boolean;
  objetivosAdecuados?: boolean;
  metodologiaAdecuada?: boolean;
  resultadosAdecuados?: boolean;
  tablasFigurasAdecuadas?: boolean;
  conclusionesAdecuadas?: boolean;
  tipoTrabajoEvaluado?: TipoTrabajo;
  modalidadRecomendada?: ModalidadPresentacion;
  comentariosAutores?: string;
  comentariosComision?: string;
  archivoCorrecciones?: string;
  trabajo: Trabajo;
  evaluador: Usuario;
  asignacion?: AsignacionEvaluacion;
}