import { TipoRol } from "./TipoRol.enum.model";

export interface Rol {
  id?: number; // Opcional por ser autogenerado en el backend
  nombre: TipoRol; // Obligatorio por restricciones de base de datos (nullable = false)
}