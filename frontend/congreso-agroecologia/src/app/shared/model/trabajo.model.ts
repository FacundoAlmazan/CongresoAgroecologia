export enum ModalidadPresentacion {
  // Definir los valores correspondientes al enum de Java, por ejemplo:
  // PRESENCIAL = 'PRESENCIAL',
  // VIRTUAL = 'VIRTUAL'
}

export enum TipoTrabajo {
  // Definir los valores correspondientes al enum de Java
}

export enum EstadoTrabajo {
  BORRADOR = 'BORRADOR',
  // Agregar el resto de los estados de la aplicación
}
/*

export interface Trabajo {
  id?: number; // Opcional si se genera en el backend al crear el registro
  titulo: string;
  resumen?: string;
  archivo?: string;
  fechaEnvio?: string; // Formato string ISO (YYYY-MM-DD) recomendado para la serialización
  passwordTrabajo: string;
  telefonoContacto?: string;
  modalidadSolicitada?: ModalidadPresentacion;
  tipo: TipoTrabajo;
  estado: EstadoTrabajo;
  autorContacto?: Usuario;
  autores: AutorTrabajo[];
  ejesTematicos: EjeTematico[];
  asignaciones: AsignacionEvaluacion[];
  evaluaciones: Evaluacion[];
  decisionTrabajo?: DecisionTrabajo;
}*/