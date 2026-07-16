export interface UsuarioAutenticado {
  id: number;
  nombre: string;
  apellido: string;
  email: string;
  telefono?: string;
  numeroIdentificacion?: string;
  nacionalidad?: string;
  institucion?: string;
  activo: boolean;
  roles: string[];
}

export interface LoginResponse {
  token: string;
  usuario: UsuarioAutenticado;
}
