import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { UsuarioAutenticado } from '../../model/auth.model';
import { PagoService } from '../../service/pago.service';
import { Inscripcion } from '../../model/inscripcion.model';

@Component({
  selector: 'app-home',
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  usuarioActual: UsuarioAutenticado | null = null;
  inscripcionActual: Inscripcion | null = null;
  cargandoInscripcion = false;
  mensajeInscripcion = '';

  constructor(
    private authService: AuthService,
    private pagoService: PagoService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarMensajeInscripcion();
    this.usuarioActual = this.authService.getUsuarioActual();

    if (this.usuarioActual) {
      this.cargandoInscripcion = true;
      this.pagoService.obtenerMia().subscribe({
        next: inscripcion => {
          this.inscripcionActual = inscripcion;
          this.cargandoInscripcion = false;
          this.cdr.markForCheck();
        },
        error: () => {
          this.inscripcionActual = null;
          this.cargandoInscripcion = false;
          this.cdr.markForCheck();
        }
      });
    }
  }

  get estaLogueado(): boolean { return this.authService.estaLogueado(); }
  get tieneInscripcion(): boolean { return this.estaLogueado && this.inscripcionActual !== null; }
  get inscripcionPendiente(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'PENDIENTE'; }
  get inscripcionAprobada(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'APROBADO'; }
  get inscripcionObservada(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'OBSERVADO'; }
  get inscripcionRechazada(): boolean { return this.tieneInscripcion && this.inscripcionActual?.estado === 'RECHAZADO'; }

  cerrarMensajeInscripcion(): void {
    this.mensajeInscripcion = '';
  }

  private cargarMensajeInscripcion(): void {
    const estadoNavegacion = this.router.getCurrentNavigation()?.extras.state;
    const estadoHistorial = history.state as Record<string, unknown> | null;
    const mensaje = estadoNavegacion?.['mensajeInscripcion']
      ?? estadoHistorial?.['mensajeInscripcion'];

    if (typeof mensaje === 'string') {
      this.mensajeInscripcion = mensaje;
    }

    if (estadoHistorial && 'mensajeInscripcion' in estadoHistorial) {
      const { mensajeInscripcion: _mensajeConsumido, ...estadoRestante } = estadoHistorial;
      history.replaceState(estadoRestante, '');
    }
  }
}
