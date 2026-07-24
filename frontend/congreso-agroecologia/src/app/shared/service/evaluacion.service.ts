import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Evaluacion } from '../model/evaluacion.model';



@Injectable({
  providedIn: 'root'
})
export class EvaluacionService {

  private apiUrl:string = '/api/evaluaciones';

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todas las evaluaciones.
   */
  listar(): Observable<Evaluacion[]> {
    return this.http.get<Evaluacion[]>(this.apiUrl);
  }

  /**
   * Obtiene una evaluación por su id.
   */
  obtenerPorId(id: number): Observable<Evaluacion> {
    return this.http.get<Evaluacion>(`${this.apiUrl}/${id}`);
  }

  /**
   * Obtiene todas las evaluaciones de un trabajo.
   */
  listarPorTrabajo(idTrabajo: number): Observable<Evaluacion[]> {
    return this.http.get<Evaluacion[]>(`${this.apiUrl}/trabajo/${idTrabajo}`);
  }

  /**
   * Obtiene todas las evaluaciones realizadas por un evaluador.
   */
  listarPorEvaluador(idEvaluador: number): Observable<Evaluacion[]> {
    return this.http.get<Evaluacion[]>(`${this.apiUrl}/evaluador/${idEvaluador}`);
  }

  /**
   * Crea una nueva evaluación.
   */
  crear(evaluacion: Evaluacion): Observable<Evaluacion> {
    return this.http.post<Evaluacion>(this.apiUrl, evaluacion);
  }

  /**
   * Modifica una evaluación existente.
   */
  modificar(id: number, evaluacion: Evaluacion): Observable<Evaluacion> {
    return this.http.put<Evaluacion>(`${this.apiUrl}/${id}`, evaluacion);
  }

  /**
   * Elimina una evaluación.
   */
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

}