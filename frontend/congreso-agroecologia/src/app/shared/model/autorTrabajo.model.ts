import { Trabajo } from './trabajo.model';

export interface AutorTrabajo {
  id?: number;
  nombre: string;
  apellido: string;
  organizacion?: string;
  pais?: string;
  email: string;
  ordenAutor: number;
  trabajo: Trabajo;
}