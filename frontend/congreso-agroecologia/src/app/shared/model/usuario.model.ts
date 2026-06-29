import { PerfilEvaluador } from "./perfilEvaluador.model";
import { Rol } from "./rol.model";
import { TipoIdentificacion } from "./TipoIdentificacion.enum.model";

export interface Usuario {
  id?: number; // Opcional por ser autogenerado en el backend
  nombre: string;
  apellido: string;
  email: string;
  password?: string; // Se marca opcional si el backend omite la clave en las consultas de lectura
  telefono?: string;
  tipoIdentificacion?: TipoIdentificacion;
  numeroIdentificacion?: string;
  nacionalidad?: string;
  institucion?: string;
  activo: boolean;
  roles: Rol[];
  perfilEvaluador?: PerfilEvaluador;
}