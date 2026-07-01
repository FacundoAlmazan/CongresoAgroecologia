import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../shared/service/auth.service';
import { Usuario } from '../../shared/model/usuario.model';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  activo = false;
  menuUsuarioAbierto = false;
  
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  get usuarioActual(): Usuario | null {
    return this.authService.getUsuarioActual();
  }
/*
  get estaLogueado(): boolean {
    return this.usuarioActual !== null;
  }
*/
  get inicialesUsuario(): string {
    const nombre = this.usuarioActual?.nombre ?? '';
    const apellido = this.usuarioActual?.apellido ?? '';

    const inicialNombre = nombre.charAt(0);
    const inicialApellido = apellido.charAt(0);

    return `${inicialNombre}${inicialApellido}`.toUpperCase() || 'U';
  }

  toggleMenuUsuario(): void {
    this.menuUsuarioAbierto = !this.menuUsuarioAbierto;
  }

  cerrarSesion(): void {
    this.authService.logout();
    localStorage.removeItem('inscripcionActual');

    this.menuUsuarioAbierto = false;

    this.router.navigate(['/home']).then(() => {
      window.location.reload();
    });
  }
}