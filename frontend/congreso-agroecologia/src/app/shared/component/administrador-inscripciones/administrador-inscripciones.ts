import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Inscripcion } from '../../model/inscripcion.model';
import { EstadoPago } from '../../model/EstadoPago.enum.model';
import { PagoService } from '../../service/pago.service';
import { formatearFechaApi } from '../../util/fecha-api';

@Component({
  selector: 'app-administrador-inscripciones',
  imports: [RouterLink, FormsModule],
  templateUrl: './administrador-inscripciones.html',
  styleUrl: './administrador-inscripciones.css',
})
export class AdministradorInscripciones implements OnInit {
  inscripciones: Inscripcion[] = [];
  estadoSeleccionado: EstadoPago | null = EstadoPago.PENDIENTE;
  busqueda = '';
  cargando = false;
  error = '';

  readonly estados = [
    EstadoPago.PENDIENTE,
    EstadoPago.OBSERVADO,
    EstadoPago.APROBADO,
    EstadoPago.RECHAZADO
  ];

  constructor(
    private pagoService: PagoService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(estado: EstadoPago | null = this.estadoSeleccionado): void {
    this.estadoSeleccionado = estado;
    this.cargando = true;
    this.error = '';
    this.pagoService.listar(estado ?? undefined).subscribe({
      next: inscripciones => {
        this.inscripciones = inscripciones;
        this.cargando = false;
        this.cdr.markForCheck();
      },
      error: err => {
        this.error = err?.error?.error ?? 'No se pudieron cargar las inscripciones.';
        this.cargando = false;
        this.cdr.markForCheck();
      }
    });
  }

  get filtradas(): Inscripcion[] {
    const texto = this.busqueda.trim().toLowerCase();
    if (!texto) return this.inscripciones;
    return this.inscripciones.filter(i => {
      const usuario = i.usuario;
      return [
        usuario.nombre,
        usuario.apellido,
        usuario.email,
        usuario.numeroIdentificacion
      ].some(valor => String(valor ?? '').toLowerCase().includes(texto));
    });
  }

  etiqueta(estado: EstadoPago): string {
    return {
      PENDIENTE: 'Pendientes',
      OBSERVADO: 'Observadas',
      APROBADO: 'Aprobadas',
      RECHAZADO: 'Rechazadas'
    }[estado];
  }

  formatearFecha(fecha: Inscripcion['fechaRegistro']): string {
    return formatearFechaApi(fecha);
  }
}
