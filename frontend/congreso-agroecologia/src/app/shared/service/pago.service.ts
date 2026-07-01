import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inscripcion } from '../model/inscripcion.model';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private apiUrl = '/api/pagos';

  constructor(private http: HttpClient) {}

  registrar(inscripcion: Inscripcion): Observable<Inscripcion> {
    return this.http.post<Inscripcion>(this.apiUrl, inscripcion);
  }

  listar(): Observable<Inscripcion[]> {
    return this.http.get<Inscripcion[]>(this.apiUrl);
  }
}