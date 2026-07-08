import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AsignacionEvaluacion } from '../model/asignacionEvaluacion.model';


@Injectable({
  providedIn: 'root'
})
export class AsignacionService {

  private apiUrl = '/api/asignaciones-evaluacion';

  constructor(private http: HttpClient) {}

  listar(): Observable<AsignacionEvaluacion[]> {
    return this.http.get<AsignacionEvaluacion[]>(this.apiUrl);
  }

  obtener(id: number): Observable<AsignacionEvaluacion> {
    return this.http.get<AsignacionEvaluacion>(`${this.apiUrl}/${id}`);
  }

  crear(asignacion: AsignacionEvaluacion): Observable<AsignacionEvaluacion> {
    return this.http.post<AsignacionEvaluacion>(this.apiUrl, asignacion);
  }

  

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  aceptar(id: number): Observable<AsignacionEvaluacion> {
    return this.http.put<AsignacionEvaluacion>(
      `${this.apiUrl}/${id}/aceptar`,
      {}
    );
  }

  rechazar(id: number): Observable<AsignacionEvaluacion> {
    return this.http.put<AsignacionEvaluacion>(
      `${this.apiUrl}/${id}/rechazar`,
      {}
    );
  }

}