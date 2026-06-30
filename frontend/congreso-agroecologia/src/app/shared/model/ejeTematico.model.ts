import { Trabajo } from './trabajo.model';

export interface EjeTematico {
  id?: number;
  nombre: string;
  descripcion?: string;
  trabajos: Trabajo[];
}