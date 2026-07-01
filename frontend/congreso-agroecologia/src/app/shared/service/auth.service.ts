import { Injectable } from '@angular/core';
import { map, Observable, tap } from 'rxjs';
import { Usuario } from '../model/usuario.model';
import { UsuarioService } from './usuario.service';
import { TipoRol } from '../model/TipoRol.enum.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly storageKey = 'usuarioActual';

  constructor(private usuarioService: UsuarioService) {}

  login(numeroIdentificacion: string, password: string): Observable<Usuario> {
    return this.usuarioService.listar().pipe(
      map(usuarios => {
        const usuario = usuarios.find(u =>
          u.numeroIdentificacion === numeroIdentificacion &&
          u.password === password
        );

        if (!usuario) {
          throw new Error('DNI o contraseña incorrectos');
        }

        return usuario;
      }),
      tap(usuario => {
        localStorage.setItem(this.storageKey, JSON.stringify(usuario));
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.storageKey);
  }

  estaLogueado(): boolean {
    return this.getUsuarioActual() !== null;
  }

  getUsuarioActual(): Usuario | null {
    const usuarioJson = localStorage.getItem(this.storageKey);
    return usuarioJson ? JSON.parse(usuarioJson) as Usuario : null;
  }

    tieneRol(nombreRol: TipoRol): boolean {
    const usuario = this.getUsuarioActual();

    if (!usuario?.roles) {
        return false;
    }

    return usuario.roles.some(rol => rol.nombre === nombreRol);
    }
    
}