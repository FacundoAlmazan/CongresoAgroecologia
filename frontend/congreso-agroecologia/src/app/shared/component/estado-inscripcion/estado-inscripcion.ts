import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Inscripcion } from '../../model/inscripcion.model';
import { EstadoPago } from '../../model/EstadoPago.enum.model';
import { PagoService } from '../../service/pago.service';
import { formatearFechaApi } from '../../util/fecha-api';

@Component({
  selector: 'app-estado-inscripcion',
  imports: [RouterLink],
  templateUrl: './estado-inscripcion.html',
  styleUrl: './estado-inscripcion.css',
})
export class EstadoInscripcion implements OnInit {
  inscripcion: Inscripcion | null = null;
  cargando = true;
  error = '';
  readonly EstadoPago = EstadoPago;

  constructor(
    private pagoService: PagoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.pagoService.obtenerMia().subscribe({
      next: inscripcion => {
        this.inscripcion = inscripcion;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: err => {
        this.error = err?.error?.error ?? 'No se pudo consultar la inscripción.';
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }

  formatearFecha(fecha: Inscripcion['fechaRegistro']): string {
    return formatearFechaApi(fecha);
  }
}
