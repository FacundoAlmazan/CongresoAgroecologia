import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../model/usuario.model';
import { TipoRol } from '../model/TipoRol.enum.model';


@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = '/api/usuarios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  crear(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  obtenerPorId(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  asignarRol(id: number, rol: TipoRol): Observable<Usuario> {
    return this.http.put<Usuario>(
      `${this.apiUrl}/${id}/roles/${rol}`,
      {} // cuerpo vacío
    );
  }

  quitarRol(id: number, rol: TipoRol): Observable<Usuario> {
    return this.http.delete<Usuario>(
      `${this.apiUrl}/${id}/roles/${rol}`
    );
  }
}