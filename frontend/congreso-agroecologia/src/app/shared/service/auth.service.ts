import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { LoginResponse, UsuarioAutenticado } from '../model/auth.model';
import { TipoRol } from '../model/TipoRol.enum.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = '/api/auth';
  private readonly tokenKey = 'token';
  private readonly usuarioKey = 'usuarioActual';

  private readonly usuarioActualSignal =
    signal<UsuarioAutenticado | null>(null);

  readonly usuarioActual = this.usuarioActualSignal.asReadonly();

  constructor(private http: HttpClient) {
    this.restaurarSesion();
  }

  login(
    numeroIdentificacion: string,
    password: string
  ): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, {
      numeroIdentificacion,
      password
    }).pipe(
      tap(respuesta => {
        localStorage.setItem(this.tokenKey, respuesta.token);
        localStorage.setItem(
          this.usuarioKey,
          JSON.stringify(respuesta.usuario)
        );

        this.usuarioActualSignal.set(respuesta.usuario);
      })
    );
  }

  logout(): void {
    this.limpiarSesion();
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  estaLogueado(): boolean {
    const token = this.getToken();

    // Solo consulta el estado. No modifica señales ni localStorage.
    return token !== null && !this.tokenVencido(token);
  }

  getUsuarioActual(): UsuarioAutenticado | null {
    return this.usuarioActualSignal();
  }

  tieneRol(rol: TipoRol | string): boolean {
    return this.usuarioActualSignal()?.roles?.includes(String(rol)) ?? false;
  }

  private restaurarSesion(): void {
    const token = this.getToken();

    if (!token || this.tokenVencido(token)) {
      this.limpiarSesion();
      return;
    }

    const usuario = this.leerUsuarioGuardado();

    if (!usuario) {
      this.limpiarSesion();
      return;
    }

    this.usuarioActualSignal.set(usuario);
  }

  private leerUsuarioGuardado(): UsuarioAutenticado | null {
    const usuarioJson = localStorage.getItem(this.usuarioKey);

    if (!usuarioJson) {
      return null;
    }

    try {
      return JSON.parse(usuarioJson) as UsuarioAutenticado;
    } catch {
      return null;
    }
  }

  private tokenVencido(token: string): boolean {
    try {
      const partes = token.split('.');

      if (partes.length != 3) {
        return true;
      }

      const payloadBase64 = partes[1]
        .replace(/-/g, '+')
        .replace(/_/g, '/');

      const payloadConRelleno = payloadBase64.padEnd(
        payloadBase64.length + (4 - payloadBase64.length % 4) % 4,
        '='
      );

      const payload = JSON.parse(atob(payloadConRelleno));

      return typeof payload.exp !== 'number'
        || payload.exp * 1000 <= Date.now();
    } catch {
      return true;
    }
  }

  private limpiarSesion(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.usuarioKey);
    localStorage.removeItem('inscripcionActual');
    this.usuarioActualSignal.set(null);
  }
}