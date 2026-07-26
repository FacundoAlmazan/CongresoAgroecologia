import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AsignacionEvaluacion } from '../../../model/asignacionEvaluacion.model';
import { EstadoAsignacion } from '../../../model/EstadoAsignacion.enum.model';
import { Trabajo } from '../../../model/trabajo.model';
import { AsignacionService } from '../../../service/asignacion.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-vista-asignacion',
  imports: [CommonModule, RouterLink],
  templateUrl: './vista-asignacion.html',
  styleUrl: './vista-asignacion.css',
})
export class VistaAsignacion {
  idEvaluador?: number;
  asignaciones: AsignacionEvaluacion[] = [];
  asignacionesAceptadas: AsignacionEvaluacion[] = [];
  misAsignaciones: AsignacionEvaluacion[] = [];
  asignacionesFinalizadas: AsignacionEvaluacion[] = [];
  cargando = true;
  mensajeError = '';

  btnStyle = '';
  asignados = true;
  peticiones = false;
  evaluados = false;

  constructor(
    private asignacionService: AsignacionService,
    private authService: AuthService,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.getUsuarioActual();
    if (!usuario) {
      this.cargando = false;
      this.mensajeError = 'La sesión no es válida. Volvé a iniciar sesión.';
      void this.router.navigate(['/login']);
      return;
    }

    this.idEvaluador = usuario.id;
    this.cargarAsignaciones();
  }

  dirigir(asignacion: AsignacionEvaluacion): void {
    void this.router.navigate(
      ['/formulario_asignacion', asignacion.id],
      { state: { asignacion } },
    );
  }

  dirigirFormEvaluacion(trabajo: Trabajo): void {
    void this.router.navigate(
      ['/formulario_evaluacion', trabajo.id],
      { state: { trabajo } },
    );
  }

  cargarAsignaciones(): void {
    this.asignacionService.listarMias().subscribe({
      next: asignaciones => {
        this.misAsignaciones = asignaciones;
        if (this.idEvaluador) {
          this.filtrarAsignaciones(this.idEvaluador);
        }
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: error => {
        console.error('Error al obtener las asignaciones', error);
        this.cargando = false;
        this.mensajeError = 'No se pudieron cargar las asignaciones.';
        this.cdr.markForCheck();
      },
    });
  }

  cambiarPestania(nro: number): void {
    this.asignados = nro === 1;
    this.peticiones = nro === 2;
    this.evaluados = nro === 3;
    this.cdr.markForCheck();
  }

  private filtrarAsignaciones(idEvaluador: number): void {
    this.misAsignaciones = this.misAsignaciones.filter(
      asignacion => asignacion.evaluador.id === idEvaluador,
    );

    this.asignaciones = this.misAsignaciones.filter(
      asignacion => asignacion.estado === EstadoAsignacion.PENDIENTE,
    );

    const aceptadas = this.misAsignaciones.filter(
      asignacion => asignacion.estado === EstadoAsignacion.ACEPTADA,
    );

    this.asignacionesFinalizadas = aceptadas.filter(
      asignacion => asignacion.finalizado,
    );
    this.asignacionesAceptadas = aceptadas.filter(
      asignacion => !asignacion.finalizado,
    );
  }
}
  
