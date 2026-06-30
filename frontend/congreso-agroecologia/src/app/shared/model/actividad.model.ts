export interface CronogramaRef {
  id: number;
}

export interface Actividad {
  id?: number;
  titulo: string;
  descripcion?: string;
  aula: string;
  fecha: string | number[];
  horaInicio: string | number[];
  horaFin: string | number[];
  cronograma: CronogramaRef;
}