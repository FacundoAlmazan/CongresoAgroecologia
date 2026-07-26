import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Inscripcion } from '../../model/inscripcion.model';
import { PagoService } from '../../service/pago.service';

@Component({
  selector: 'app-detalle-inscripcion',
  imports: [FormsModule],
  templateUrl: './detalle-inscripcion.html',
  styleUrl: './detalle-inscripcion.css',
})
export class DetalleInscripcion implements OnInit {
  inscripcion: Inscripcion | null = null;
  comentario = '';
  montoAbonado = 0;
  cargando = true;
  procesando = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pagoService: PagoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.error = 'La inscripción solicitada no es válida.';
      this.cargando = false;
      return;
    }
    this.pagoService.obtener(id).subscribe({
      next: inscripcion => {
        this.inscripcion = inscripcion;
        this.comentario = inscripcion.comentarioRevision ?? '';
        this.montoAbonado = inscripcion.montoAbonado ?? inscripcion.monto;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: err => {
        this.error = err?.error?.error ?? 'No se pudo obtener la inscripción.';
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }

  get diferencia(): number {
    return Math.max(0, (this.inscripcion?.monto ?? 0) - (this.montoAbonado ?? 0));
  }

  aprobar(): void {
    if (!this.inscripcion?.id) return;
    this.ejecutar(this.pagoService.aprobar(this.inscripcion.id, this.montoAbonado));
  }

  observar(): void {
    if (!this.inscripcion?.id) return;
    if (!this.comentario.trim()) {
      this.error = 'Escribí qué debe corregir el participante.';
      return;
    }
    this.ejecutar(this.pagoService.observar(
      this.inscripcion.id,
      this.comentario.trim(),
      this.montoAbonado
    ));
  }

  rechazar(): void {
    if (!this.inscripcion?.id) return;
    if (!this.comentario.trim()) {
      this.error = 'Indicá el motivo del rechazo.';
      return;
    }
    this.ejecutar(this.pagoService.rechazar(this.inscripcion.id, this.comentario.trim()));
  }

  descargar(contenido?: string, nombre = 'archivo'): void {
    if (!contenido) return;
    const enlace = document.createElement('a');
    enlace.href = contenido;
    enlace.download = nombre;
    enlace.click();
  }

  private ejecutar(solicitud: ReturnType<PagoService['aprobar']>): void {
    this.procesando = true;
    this.error = '';
    solicitud.subscribe({
      next: () => {
        this.procesando = false;
        this.cdr.markForCheck();
        this.router.navigate(['/lista_inscripciones']);
      },
      error: err => {
        this.error = err?.error?.error ?? 'No se pudo actualizar la inscripción.';
        this.procesando = false;
        this.cdr.markForCheck();
      }
    });
  }
}
