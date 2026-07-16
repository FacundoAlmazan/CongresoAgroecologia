import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { UsuarioAutenticado } from '../../model/auth.model';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  usuarioActual: UsuarioAutenticado | null = null;
  inscripcionActual: any = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.usuarioActual = this.authService.getUsuarioActual();

    const inscripcionJson = localStorage.getItem('inscripcionActual');
    const inscripcionGuardada = inscripcionJson ? JSON.parse(inscripcionJson) : null;

    this.inscripcionActual = this.usuarioActual &&
      inscripcionGuardada?.usuario?.id === this.usuarioActual.id
        ? inscripcionGuardada
        : null;
  }

  get estaLogueado(): boolean { return this.authService.estaLogueado(); }
  get tieneInscripcion(): boolean { return this.estaLogueado && this.inscripcionActual !== null; }
  get inscripcionPendiente(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'PENDIENTE'; }
  get inscripcionAprobada(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'APROBADO'; }
}
