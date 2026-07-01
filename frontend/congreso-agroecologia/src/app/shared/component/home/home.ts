import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  usuarioActual: any = null;
  inscripcionActual: any = null;

  ngOnInit(): void {
    const usuarioJson = localStorage.getItem('usuarioActual');
    this.usuarioActual = usuarioJson ? JSON.parse(usuarioJson) : null;

    const inscripcionJson = localStorage.getItem('inscripcionActual');
    const inscripcionGuardada = inscripcionJson ? JSON.parse(inscripcionJson) : null;

    if (
      this.usuarioActual &&
      inscripcionGuardada &&
      inscripcionGuardada.usuario?.id === this.usuarioActual.id
    ) {
      this.inscripcionActual = inscripcionGuardada;
    } else {
      this.inscripcionActual = null;
    }
  }

  get estaLogueado(): boolean {
    return this.usuarioActual !== null;
  }

  get tieneInscripcion(): boolean {
    return this.estaLogueado && this.inscripcionActual !== null;
  }

  get inscripcionPendiente(): boolean {
    return this.tieneInscripcion && this.inscripcionActual?.estado === 'PENDIENTE';
  }

  get inscripcionAprobada(): boolean {
    return this.tieneInscripcion && this.inscripcionActual?.estado === 'APROBADO';
  }
}