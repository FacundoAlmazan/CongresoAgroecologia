import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Evaluacion } from '../../model/evaluacion.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
import { EvaluacionService } from '../../service/evaluacion.service';

@Component({
  selector: 'app-evaluaciones',
  imports: [],
  templateUrl: './evaluaciones.html',
  styleUrl: './evaluaciones.css',
})
export class Evaluaciones implements OnInit {
  readonly Recomendacion = Recomendacion;

  evaluaciones: Evaluacion[] = [];
  idTrabajo?: number;
  cargando = true;
  mensajeError = '';

  constructor(
    private evaluacionService: EvaluacionService,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    const idTrabajo = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isInteger(idTrabajo) || idTrabajo <= 0) {
      this.cargando = false;
      this.mensajeError = 'No se indicó un trabajo válido.';
      return;
    }

    this.idTrabajo = idTrabajo;
    this.cargarEvaluaciones(idTrabajo);
  }

  cargarEvaluaciones(idTrabajo: number): void {
    this.evaluacionService.listarPorTrabajo(idTrabajo).subscribe({
      next: evaluaciones => {
        this.evaluaciones = evaluaciones;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: error => {
        console.error('Error al cargar las evaluaciones:', error);
        this.evaluaciones = [];
        this.cargando = false;
        this.mensajeError = this.obtenerMensajeError(error);
        this.cdr.markForCheck();
      },
    });
  }

  dirigir(evaluacion: Evaluacion): void {
    if (!evaluacion.id) {
      this.mensajeError = 'La evaluación seleccionada no tiene un identificador válido.';
      return;
    }

    void this.router.navigate(
      ['/resultado_evaluacion', evaluacion.id],
      { state: { evaluacion } },
    );
  }

  private obtenerMensajeError(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
      if (typeof error.error === 'string' && error.error.trim()) {
        return error.error;
      }
      if (typeof error.error?.error === 'string') {
        return error.error.error;
      }
      if (typeof error.error?.message === 'string') {
        return error.error.message;
      }
    }
    return 'No se pudieron cargar las evaluaciones.';
  }
}
