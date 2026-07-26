import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Evaluacion } from '../../model/evaluacion.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
import { EvaluacionService } from '../../service/evaluacion.service';

@Component({
  selector: 'app-resultado-evaluacion',
  imports: [],
  templateUrl: './resultado-evaluacion.html',
  styleUrl: './resultado-evaluacion.css',
})
export class ResultadoEvaluacion implements OnInit {
  readonly Recomendacion = Recomendacion;

  evaluacion?: Evaluacion;
  cargando = true;
  mensajeError = '';

  constructor(
    private evaluacionService: EvaluacionService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    const evaluacionNavegada =
      (history.state as { evaluacion?: Evaluacion } | null)?.evaluacion;

    if (evaluacionNavegada?.id && (!Number.isInteger(id) || evaluacionNavegada.id === id)) {
      this.evaluacion = evaluacionNavegada;
      this.cargando = false;
      return;
    }

    if (!Number.isInteger(id) || id <= 0) {
      this.cargando = false;
      this.mensajeError = 'No se indicó una evaluación válida.';
      return;
    }

    this.evaluacionService.obtenerPorId(id).subscribe({
      next: evaluacion => {
        this.evaluacion = evaluacion;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: error => {
        this.cargando = false;
        this.mensajeError = this.obtenerMensajeError(error);
        this.cdr.markForCheck();
      },
    });
  }

  private obtenerMensajeError(error: unknown): string {
    if (error instanceof HttpErrorResponse) {
      if (typeof error.error?.error === 'string') {
        return error.error.error;
      }
      if (typeof error.error?.message === 'string') {
        return error.error.message;
      }
    }
    return 'No se pudo cargar la evaluación.';
  }
}
