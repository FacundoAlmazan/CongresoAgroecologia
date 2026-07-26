import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inscripcion } from '../model/inscripcion.model';
import { EstadoPago } from '../model/EstadoPago.enum.model';

@Injectable({ providedIn: 'root' })
export class PagoService {
  private apiUrl = '/api/pagos';

  constructor(private http: HttpClient) {}

  registrar(inscripcion: Inscripcion): Observable<Inscripcion> {
    return this.http.post<Inscripcion>(this.apiUrl, inscripcion);
  }

  listar(estado?: EstadoPago): Observable<Inscripcion[]> {
    const url = estado ? `${this.apiUrl}?estado=${estado}` : this.apiUrl;
    return this.http.get<Inscripcion[]>(url);
  }

  obtener(id: number): Observable<Inscripcion> {
    return this.http.get<Inscripcion>(`${this.apiUrl}/${id}`);
  }

  obtenerMia(): Observable<Inscripcion | null> {
    return this.http.get<Inscripcion | null>(`${this.apiUrl}/mio`);
  }

  aprobar(id: number, montoAbonado?: number): Observable<Inscripcion> {
    return this.http.put<Inscripcion>(`${this.apiUrl}/${id}/aprobar`, { montoAbonado });
  }

  observar(id: number, comentario: string, montoAbonado?: number): Observable<Inscripcion> {
    return this.http.put<Inscripcion>(`${this.apiUrl}/${id}/observar`, {
      comentario,
      montoAbonado
    });
  }

  rechazar(id: number, motivo: string): Observable<Inscripcion> {
    return this.http.put<Inscripcion>(`${this.apiUrl}/${id}/rechazar`, { motivo });
  }

  reenviar(inscripcion: Inscripcion): Observable<Inscripcion> {
    return this.http.put<Inscripcion>(`${this.apiUrl}/mio/reenviar`, inscripcion);
  }
}
