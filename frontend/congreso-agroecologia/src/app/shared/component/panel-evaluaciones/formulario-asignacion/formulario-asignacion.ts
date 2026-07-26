import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { InfoPaper } from '../../info-paper/info-paper';
import { AsignacionEvaluacion } from '../../../model/asignacionEvaluacion.model';
import { AsignacionService } from '../../../service/asignacion.service';

@Component({
  selector: 'app-formulario-asignacion',
  imports: [CommonModule, InfoPaper, RouterLink],
  templateUrl: './formulario-asignacion.html',
  styleUrl: './formulario-asignacion.css',
})
export class FormularioAsignacion implements OnInit {
  asignacion?: AsignacionEvaluacion;
  seleccion = false;
  aceptado = false;
  cargando = true;
  mensaje = '';

  constructor(
    private asignacionService: AsignacionService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const asignacionId = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isInteger(asignacionId) || asignacionId <= 0) {
      this.cargando = false;
      this.mensaje = 'No se indicó una asignación válida.';
      return;
    }

    const asignacionNavegada =
      (history.state as { asignacion?: AsignacionEvaluacion } | null)?.asignacion;
    if (asignacionNavegada?.id === asignacionId) {
      this.asignacion = asignacionNavegada;
      this.cargando = false;
      return;
    }

    this.asignacionService.obtenerMia(asignacionId).subscribe({
      next: asignacion => {
        this.asignacion = asignacion;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: error => {
        console.error('Error al obtener la asignación', error);
        this.cargando = false;
        this.mensaje = 'No se pudo cargar la asignación.';
        this.cdr.markForCheck();
      },
    });
  }

  enviar(): void {
    if (!this.asignacion?.id) {
      this.mensaje = 'No hay una asignación válida para responder.';
      return;
    }

    if (!this.seleccion) {
      this.mensaje = 'Debe seleccionar una opción.';
      this.cdr.markForCheck();
      return;
    }

    const peticion = this.aceptado
      ? this.asignacionService.aceptar(this.asignacion.id)
      : this.asignacionService.rechazar(this.asignacion.id);

    peticion.subscribe({
      next: asignacion => {
        this.asignacion = asignacion;
        this.mensaje = this.aceptado
          ? 'La asignación fue aceptada correctamente.'
          : 'La asignación fue rechazada correctamente.';
        this.cdr.markForCheck();
        setTimeout(() => {
          void this.router.navigate(['/trabajos_asignados']);
        }, 1500);
      },
      error: error => {
        console.error('Error al responder la asignación', error);
        this.mensaje = 'No se pudo registrar la respuesta. Intente nuevamente.';
        this.cdr.markForCheck();
      },
    });
  }
}
