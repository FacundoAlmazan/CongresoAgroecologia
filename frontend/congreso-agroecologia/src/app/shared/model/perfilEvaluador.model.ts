

export interface DisponibilidadEvaluadorEje {
  id?: number;
  ejeTematico?: {
    id?: number;
    nombre?: string;
  };
  cantidad?: number;
}

export interface PerfilEvaluador {
  id?: number;
  evaluadorEdicionesPasadas: boolean;
  evaluadorOtrosCongresos: boolean;
  formacionAcademica?: string;
  areasConocimiento?: string;
  subareasConocimiento?: string;
  comentario?: string;
  disponibilidades?: DisponibilidadEvaluadorEje[];
}