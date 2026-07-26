import { CommonModule, Location } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { InfoPaper } from '../info-paper/info-paper';
import { Evaluacion } from '../../model/evaluacion.model';
import { Trabajo } from '../../model/trabajo.model';
import { Usuario } from '../../model/usuario.model';
import { Recomendacion } from '../../model/Recomendacion.enum.model';
import { AuthService } from '../../service/auth.service';
import { EvaluacionService } from '../../service/evaluacion.service';
import { TrabajoService } from '../../service/trabajo.service';

@Component({
  selector: 'app-formulario-evaluacion',
  imports: [CommonModule, FormsModule, InfoPaper],
  templateUrl: './formulario-evaluacion.html',
  styleUrl: './formulario-evaluacion.css',
})
export class FormularioEvaluacion implements OnInit {
  readonly Recomendacion = Recomendacion;

  evaluacion?: Evaluacion;
  trabajo?: Trabajo;
  cargando = true;
  enviando = false;
  mensajeError = '';

  constructor(
    private evaluacionService: EvaluacionService,
    private trabajoService: TrabajoService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router,
    private location: Location,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    const usuario = this.authService.getUsuarioActual();
    if (!usuario) {
      this.mostrarError('La sesión no es válida. Volvé a iniciar sesión.');
      void this.router.navigate(['/login']);
      return;
    }

    const trabajoId = Number(this.route.snapshot.paramMap.get('id'));
    if (!Number.isInteger(trabajoId) || trabajoId <= 0) {
      this.mostrarError('No se indicó un trabajo válido para evaluar.');
      return;
    }

    const trabajoNavegado = (history.state as { trabajo?: Trabajo } | null)?.trabajo;
    if (trabajoNavegado?.id === trabajoId) {
      this.prepararFormulario(trabajoNavegado, usuario.id);
      return;
    }

    this.trabajoService.get(trabajoId).subscribe({
      next: trabajo => this.prepararFormulario(trabajo, usuario.id),
      error: error => this.mostrarError(this.obtenerMensajeError(
        error,
        'No se pudo cargar el trabajo que querés evaluar.',
      )),
    });
  }

  enviar(): void {
    if (!this.evaluacion || !this.trabajo || this.enviando) {
      return;
    }

    this.enviando = true;
    this.mensajeError = '';
    this.evaluacionService.crear({
      ...this.evaluacion,
      trabajo: this.trabajo,
    }).subscribe({
      next: evaluacionCreada => {
        this.enviando = false;
        this.evaluacion = evaluacionCreada;
        this.cdr.markForCheck();

        if (evaluacionCreada.id) {
          void this.router.navigate(
            ['/resultado_evaluacion', evaluacionCreada.id],
            { state: { evaluacion: evaluacionCreada } },
          );
        }
      },
      error: error => {
        this.enviando = false;
        this.mensajeError = this.obtenerMensajeError(
          error,
          'No se pudo enviar la evaluación.',
        );
        this.cdr.markForCheck();
      },
    });
  }

  cancelar(): void {
    this.location.back();
  }

  private prepararFormulario(trabajo: Trabajo, evaluadorId: number): void {
    this.trabajo = trabajo;
    this.evaluacion = this.inicializarEvaluacion(trabajo, evaluadorId);
    this.cargando = false;
    this.mensajeError = '';
    this.cdr.markForCheck();
  }

  private inicializarEvaluacion(trabajo: Trabajo, evaluadorId: number): Evaluacion {
    return {
      recomendacion: Recomendacion.APROBADO_CON_CORRECCION,
      comentarios: '',
      pertinenteCongreso: false,
      identificoAutores: false,
      conflictoIntereses: false,
      ejeTematicoCorrecto: false,
      lenguajeAdecuado: false,
      tituloAdecuado: false,
      resumenAdecuado: false,
      palabrasClaveAdecuadas: false,
      bibliografiaApa: false,
      bibliografiaCompleta: false,
      introduccionAdecuada: false,
      objetivosAdecuados: false,
      metodologiaAdecuada: false,
      resultadosAdecuados: false,
      tablasFigurasAdecuadas: false,
      conclusionesAdecuadas: false,
      tipoTrabajoEvaluado: undefined,
      modalidadRecomendada: undefined,
      comentariosAutores: '',
      comentariosComision: '',
      archivoCorrecciones: '',
      trabajo,
      evaluador: { id: evaluadorId } as Usuario,
    };
  }

  private mostrarError(mensaje: string): void {
    this.cargando = false;
    this.mensajeError = mensaje;
    this.cdr.markForCheck();
  }

  private obtenerMensajeError(error: unknown, predeterminado: string): string {
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
    return predeterminado;
  }
}
